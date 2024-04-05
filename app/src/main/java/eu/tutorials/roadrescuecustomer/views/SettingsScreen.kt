package eu.tutorials.roadrescuecustomer.views

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.R
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navigationToDashboardScreen: () -> Unit,
    navigationToProfileScreen: () -> Unit,
    navigationToTrackLocationScreen: () -> Unit,
    navigationToActivitiesScreen: () -> Unit,
    context: Context,
    navController: NavHostController
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
                Column{
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Settings",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = textStyle1
                    )
                    SettingsBox()
                }
            }
        }
    }
}

@Composable
fun SettingsBox() {
    var showManagePaymentMethodsWindow by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White), shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)) // Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            //Just for testing...
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                FillDetailsButton(detailButtonName = "Change Phone Number") {
                    //todo
                }
                CircularProgressBar(isDisplayed = true)
            }
            //----------------
            FillDetailsButton(detailButtonName = "Manage Payment Methods") {
                showManagePaymentMethodsWindow = true
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if(showManagePaymentMethodsWindow) {
        ManagePaymentMethodsWindow {
            showManagePaymentMethodsWindow = false
        }
    }
}

@Composable
fun ManagePaymentMethodsWindow(onDismiss: ()->Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .border(2.dp, Color.White, shape = RoundedCornerShape(10))
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        tonalElevation = 16.dp,
        containerColor = Color(0xFFDCE4EC),
        confirmButton = {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Manage Payment Methods",
                    textAlign = TextAlign.Center,
                    color = Color(0xFF253555),
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField("Name on Card", "", 250)
                TextField("Card Number", "", 250)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    TextField(labelName = "Expiry Date", value = "", width = 50)
                    TextField(labelName = "CVV", value = "", width = 50)
                }
            }
        }
    )
}

@Composable
fun TextField(labelName: String, value: String?, width: Int): String {
    var fieldValue by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
            OutlinedTextField(
                value = fieldValue ?: "",
                onValueChange = { fieldValue = it },
                modifier = Modifier
                    .height(50.dp)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(50))
                    .shadow(6.dp, shape = RoundedCornerShape(50))
                    .background(Color.White)
                    .width(width.dp),
                textStyle = textStyle2,
                placeholder = {
                    Text(
                        text = labelName,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true
            )
    }
    Spacer(modifier = Modifier.height(8.dp))
    return fieldValue ?: ""
}
