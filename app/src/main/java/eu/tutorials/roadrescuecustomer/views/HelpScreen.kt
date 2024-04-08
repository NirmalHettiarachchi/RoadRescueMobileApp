package eu.tutorials.roadrescuecustomer.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.annotation.ReturnThis
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.LocationUtils
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.DriverManager
import java.sql.ResultSet

data class CustomerSupportTicket(
    val id: Int,
    val issue: String,
    val date: String,
    val status: String,
    val description: String
    // Add other fields as necessary
)

suspend fun fetchCustomerSupportTickets(): List<CustomerSupportTicket> = withContext(Dispatchers.IO) {
    val tickets = mutableListOf<CustomerSupportTicket>()

    val databaseUrl = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/road_rescue"
    val databaseUser = "admin"
    val databasePassword = "admin123"

    try {
        Class.forName("com.mysql.jdbc.Driver")
        DriverManager.getConnection(databaseUrl, databaseUser, databasePassword).use { connection ->
            connection.createStatement().use { statement ->
                val resultSet = statement.executeQuery("SELECT * FROM customer_support_ticket")
                while (resultSet.next()) {
                    tickets.add(resultSetToTicket(resultSet))
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        // Handle exceptions properly in real code
    }

    tickets
}

fun resultSetToTicket(rs: ResultSet): CustomerSupportTicket {
    return CustomerSupportTicket(
        id = rs.getInt("id"), // Adjust field names based on your database
        issue = rs.getString("title"),
        date = rs.getString("created_time"),
        status = rs.getString("status"),
        description = rs.getString("description")
        // Add other fields as necessary
    )
}

@Composable
fun HelpScreen() {
    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                PhoneIconButton(LocalContext.current)
                Text(
                    text = "Help",
                    style = textStyle1
                )
            }
            RequestHelpBox()
            HelpRequestedList()
        }
    }
}

@Composable
fun PhoneIconButton(context: Context) {
    IconButton(
        onClick = {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+94768879830")
            context.startActivity(intent)
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.phone),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 5.dp)
                .size(30.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun HelpRequestedList() {
    val tickets = remember { mutableStateListOf<CustomerSupportTicket>() }

    LaunchedEffect(Unit) {
        tickets.addAll(fetchCustomerSupportTickets())
        Log.d("HelpScreen", "Fetched tickets: ${tickets.size}")
    }


    tickets.forEach { ticket ->
        HelpRequested(ticket)
    }
}

@Composable
fun RequestHelpBox() {
    var showContactSupportWindow by remember { mutableStateOf(false) }
    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))// Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Click on the button below to contact ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Text(
                text = "support...",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonButton(btnName = "Request Help", modifier = Modifier.align(Alignment.CenterHorizontally)) {
                showContactSupportWindow = true
            }

            Spacer(modifier = Modifier.height(16.dp))

            if(showContactSupportWindow) {
                RequestHelpWindow { showContactSupportWindow = false }
            }
        }
    }
}

@Composable
fun RequestHelpWindow(onDismiss: () -> Unit) {
    var issue by remember { mutableStateOf("") }
    var issueDetails by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        tonalElevation = 16.dp,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFDCE4EC)
            )
            .verticalScroll(rememberScrollState()),
        containerColor = Color(0xFFDCE4EC),
        confirmButton = {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Contact Support",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))

                var issueList by remember { mutableStateOf(listOf<String>()) }

                issue = dropDownCommon("Issue", issueList)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = issueDetails,
                    onValueChange = { issueDetails = it },
                    modifier = Modifier
                        .height(100.dp)
                        .border(2.dp, Color.White, shape = RoundedCornerShape(20))
                        .shadow(2.dp, shape = RoundedCornerShape(20))
                        .background(Color.White),
                    placeholder = {
                        Text(
                            text = "Write more about your issue ... ",
                            fontSize = 12.sp,
                            color = Color(0xFF253555)
                        )
                    },
                    textStyle = TextStyle(
                        color = Color(0xFF253555)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                CommonButton(btnName = "Submit", modifier = Modifier.align(Alignment.CenterHorizontally)) {
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpRequested(helpTicket: CustomerSupportTicket) {
    var showMoreDetailsWindow by remember {  mutableStateOf(false) }
    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
        onClick = { showMoreDetailsWindow = true }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "\"${helpTicket.issue}\": ${helpTicket.date} ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Text(
                text = "Status: ${helpTicket.status} ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if(showMoreDetailsWindow) {
        MoreInfoWindow(message =
        "Support Ticket ID: H34" +
                "\nCategory: Payment Issue" +
                "\nDate: 03/04/2024" +
                "\nStatus: Solved" +
                "\nDescription: None" +
                "\nSolution: Reversed the payment"
        ) {
            showMoreDetailsWindow = false
        }
    }
}