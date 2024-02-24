package eu.tutorials.roadrescuecustomer.views

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.AppPreferences
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement


@Composable
fun ProfileScreen(
    navigationToDashboardScreen: () -> Unit,
    navigationToTrackLocationScreen: () -> Unit,
    navigationToActivitiesScreen: () -> Unit,
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    context: MainActivity
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    SidebarContent({
                        scope.launch {
                            drawerState.close()
                        }
                    }, navController, context)
                }
            )
        }
    ) {
        Scaffold {
            Column(
                backgroundModifier.padding(it),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    backgroundModifier,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Header {
                            scope.launch { drawerState.open() }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Customer Profile",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = textStyle1
                        )
                        utilFun()
                        ProfileBox(profileViewModel)
                        HelpBox()
                    }
                    Footer(
                        navigationToDashboardScreen,
                        {},
                        navigationToTrackLocationScreen,
                        navigationToActivitiesScreen
                    )
                }
            }
        }
    }
}

@Throws(SQLException::class)
fun utilFun() {
    Thread {
        val DATABASE_NAME = "road_rescue"
        val url = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/" +
                DATABASE_NAME
        val username = "admin"
        val databasePassword = "admin123"
        //do your work
        val records = StringBuilder()
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val connection: Connection =
                DriverManager.getConnection(url, username, databasePassword)
            val statement: Statement = connection.createStatement()
            val rs: ResultSet = statement.executeQuery("SELECT * FROM customer")
            while (rs.next()) {
                records.append(rs.getString(2))
                    .append(rs.getString(3)).append("\n")
                    .append(rs.getString(4)).append("\n")
                    .append(rs.getString(5)).append("\n")
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("Result", records.toString())
    }.start()
}

@Composable
fun ProfileBox(profileViewModel: ProfileViewModel) {
    val context = LocalContext.current

    var isEditing by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var showPhoneNumDetailWindow by remember { mutableStateOf(false) }
    var showNumOfReqServiceWindow by remember { mutableStateOf(false) }
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
            Spacer(modifier = Modifier.height(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.profile_pic),
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Unspecified, contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            newName =
                profileField(
                    "Name",
                    AppPreferences(context).getStringPreference("NAME", ""),
                    isEditing
                )
            newEmail =
                profileField(
                    "Email",
                    AppPreferences(context).getStringPreference("EMAIL", ""),
                    isEditing
                )

            ProfileFieldButton(
                labelName = "Phone Number",
                value = AppPreferences(context).getStringPreference("PHONE", ""),
                onClickButton = { showPhoneNumDetailWindow = true }
            )
            ProfileFieldButton(
                labelName = "Number of Service Requests",
                value = profileViewModel.numOfServiceRequests.value.toString(),
                onClickButton = { showNumOfReqServiceWindow = true }
            )

            if (!isEditing) {
                //Edit button
                CommonButton(
                    btnName = "Edit Profile",
                    Modifier.align(Alignment.CenterHorizontally)
                ) {
                    isEditing = true
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    //Save Button
                    CommonButton("Save", Modifier) {
                        if (newName.isNotEmpty() && newEmail.isNotEmpty()) {
                            profileViewModel.updateProfile(
                                AppPreferences(context).getStringPreference(
                                    "PHONE",
                                    ""
                                ), newName, newEmail, context
                            )
                            isEditing = false
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    //Cancel Button
                    CommonButton("Cancel", Modifier) {
                        isEditing = false
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
    if (showPhoneNumDetailWindow) {
        MoreInfoWindow(message = "You can change the registered phone number by accessing the settings...") {
            showPhoneNumDetailWindow = false
        }
    }
    if (showNumOfReqServiceWindow) {
        MoreInfoWindow(message = "This number shows only the completed service requests that have been accepted by a service provider and completed.") {
            showNumOfReqServiceWindow = false
        }
    }
}

@Composable
fun profileField(labelName: String, value: String?, isEditing: Boolean = false): String {
    var fieldValue by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = labelName,
                style = textStyle2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fieldValue ?: "",
                onValueChange = { fieldValue = it },
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(30))
                    .shadow(6.dp, shape = RoundedCornerShape(30))
                    .background(Color.White),
                textStyle = textStyle2,
                singleLine = true,
                enabled = isEditing
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    return fieldValue ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFieldButton(labelName: String, value: String, onClickButton: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = labelName,
                style = textStyle2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Card(
                onClick = { onClickButton() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(horizontal = 38.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC6D4DE))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = value,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f),
                        maxLines = 1,
                        style = textStyle2,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.question_fill),
                        modifier = Modifier
                            .size(30.dp),
                        contentDescription = "Info",
                        tint = Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

