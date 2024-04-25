package eu.tutorials.roadrescuecustomer.views

import CustomerSupportTicketViewModel
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.CustomerSupportTicket
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HelpScreen(customerSupportTicketViewModel: CustomerSupportTicketViewModel) {
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
            RequestHelpBox(customerSupportTicketViewModel, LocalContext.current)
            HelpRequestedList(customerSupportTicketViewModel)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HelpRequestedList(customerSupportTicketViewModel: CustomerSupportTicketViewModel) {

    val loading by customerSupportTicketViewModel.loading
    CircularProgressBar(isDisplayed = loading)

    val customerId = AppPreferences(LocalContext.current).getStringPreference("CUSTOMER_ID", "")
    LaunchedEffect(Unit) {
        customerSupportTicketViewModel.clearTickets()
        customerSupportTicketViewModel.fetchCustomerSupportTickets(customerId)
    }

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))
    ) {
        if(customerSupportTicketViewModel.tickets.isEmpty()) {
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "There are no support tickets to show!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(128.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Support tickets",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(16.dp))
            customerSupportTicketViewModel.tickets.forEach { ticket ->
                HelpRequested(ticket)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun RequestHelpBox(customerSupportTicketViewModel: CustomerSupportTicketViewModel, context: Context) {
    var showContactSupportWindow by remember { mutableStateOf(false) }

    val customerId = AppPreferences(LocalContext.current).getStringPreference("CUSTOMER_ID", "")

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
                RequestHelpWindow (customerSupportTicketViewModel, customerId.toInt(), context){
                    showContactSupportWindow = false
                }
            }
        }
    }
}

@Composable
fun RequestHelpWindow(
    customerSupportTicketViewModel: CustomerSupportTicketViewModel,
    customerId: Int,
    context: Context,
    onDismiss: () -> Unit,
)
{
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

                val issueList = listOf("Login Issue", "Payment Issue", "Technical Glitch", "Feedback", "Other")

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
                    if (issue.isNotEmpty()) {
                        customerSupportTicketViewModel.writeCustomerSupportTicket(
                            customerId,
                            issue,
                            issueDetails,
                            context
                        )
                        onDismiss()
                    } else {
                        MainScope().launch {
                            Toast.makeText(context, "Select an issue", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpRequested(customerSupportTicket: CustomerSupportTicket) {
    var showMoreDetailsWindow by remember {  mutableStateOf(false) }

    val dateTime = LocalDateTime.parse(customerSupportTicket.date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
    val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { showMoreDetailsWindow = true }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${customerSupportTicket.issue} - $formattedDate $formattedTime ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle5
            )
            Text(
                text = "${customerSupportTicket.status} ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle5
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if(showMoreDetailsWindow) {
        CustomerSupportTicketMoreInfoWindow(customerSupportTicket = customerSupportTicket, formattedDate, formattedTime)
        {
            showMoreDetailsWindow = false
        }
    }
}

@Composable
fun CustomerSupportTicketMoreInfoWindow(
                                        customerSupportTicket: CustomerSupportTicket,
                                        formattedDate: String,
                                        formattedTime: String,
                                        onDismiss: () -> Unit)
{
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .border(2.dp, Color.White, shape = RoundedCornerShape(20))
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .verticalScroll(rememberScrollState()),
        tonalElevation = 16.dp,
        containerColor = Color(0xFFDCE4EC),
        confirmButton = {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Support Ticket ID ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":H${customerSupportTicket.id}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Category ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${customerSupportTicket.issue}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Created on ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":$formattedDate at $formattedTime", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Status ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${customerSupportTicket.status}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Description ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${customerSupportTicket.description}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Solution ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${customerSupportTicket.solution}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    )
}
