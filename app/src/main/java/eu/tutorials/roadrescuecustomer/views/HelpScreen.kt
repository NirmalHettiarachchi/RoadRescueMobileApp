package eu.tutorials.roadrescuecustomer.views

import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch

@Composable
fun HelpScreen(
    navigationToDashboardScreen: () -> Unit,
    navigationToProfileScreen: () -> Unit,
    navigationToTrackLocationScreen: () -> Unit,
    navigationToActivitiesScreen: () -> Unit,
    context: Context,
    navController: NavHostController,
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
        Scaffold (
            topBar = {
                Header {
                    scope.launch { drawerState.open() }
                }
            },
            bottomBar = {
                Footer(
                    navigationToDashboardScreen,
                    navigationToProfileScreen,
                    navigationToTrackLocationScreen,
                    navigationToActivitiesScreen
                )
            }
        ){
            Column(
                backgroundModifier
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row (modifier = Modifier.align(Alignment.CenterHorizontally)){
                        Icon(
                            painter = painterResource(id = R.drawable.phone),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .size(30.dp),
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "Help",
                            style = textStyle1
                        )
                    }
                    RequestHelpBox()
                }
            }
        }
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
            ),
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

                issue = dropDown("Issue", issueList)
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