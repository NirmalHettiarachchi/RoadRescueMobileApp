package com.example.garage.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/SidebarContent.kt
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel

@Composable
fun SidebarContent(
    menuClicked:(isLogOut : Boolean)->Unit,
    navHostController: NavHostController,
    context: Context,
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel
){
=======


@Composable

fun SidebarContent(menuClicked:()->Unit){

>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/SidebarContent.kt
    Column(


        modifier = Modifier

            .fillMaxHeight()

            .width(250.dp)

            .background(Color(0xFF253555))

    ) {
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/SidebarContent.kt
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(30.dp)
                        .clickable {
                            menuClicked(false)
                        },
                    contentDescription = "Localized description"
                )
                Divider()
                Spacer(modifier = Modifier
                    .background(Color.White)
                    .height(2.dp))
            }
            SidebarButton(buttonName = "Help", verticalPadding = 8) {
                navHostController.navigate("helpscreen")
                menuClicked(false)
            }
            SidebarButton(buttonName = "Settings", verticalPadding = 8) {
                navHostController.navigate("settingsscreen")
                menuClicked(false)
            }
        }
        SidebarButton(buttonName = "Log Out", verticalPadding = 16) {
            navHostController.navigate("loginscreen") {
                popUpTo("loginscreen") { inclusive = true }
            }
            AppPreferences(context).clearAllPreferences()
            currentStateViewModel.setCurrentState(
                isServiceRequested = false,
                isReqServiceWindowOpened = false
            )
            serviceRequestViewModel.clearData()
            menuClicked(true)
=======

        Column {

            Icon(
                imageVector = Icons.Filled.Menu,
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .size(30.dp)
                    .clickable {
                        menuClicked()
                    },
                contentDescription = "Localized Description"
            )

            Spacer(modifier = Modifier
                .background(Color.White)
                .height(2.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            var garageOpenClosedStatus by remember { mutableStateOf("Closed") }

            SidebarButton(buttonName=garageOpenClosedStatus,verticalPadding=8, onClick = {
                garageOpenClosedStatus=chaneState(garageOpenClosedStatus)
            })

            SidebarButton(buttonName="Activities",verticalPadding=8, onClick = {})

            SidebarButton(buttonName="Help",verticalPadding=8, onClick = {})

            SidebarButton(buttonName="Settings",verticalPadding=8, onClick = {})

>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/SidebarContent.kt
        }

        SidebarButton(buttonName="Log Out",verticalPadding=16, onClick = {})
    }

}

fun chaneState(garageOpenClosedStatus: String):String {
    var stastus=""
    if (garageOpenClosedStatus == "Closed") {
        stastus="Open"
    }else{
        stastus="Closed"
    }
    return stastus
}


@Composable

fun SidebarButton(buttonName:String,verticalPadding:Int,onClick:()->Unit){

    Button(

        onClick = onClick ,

        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),

        modifier = Modifier

            .fillMaxWidth()

            .padding(horizontal = 16.dp, vertical = verticalPadding.dp),

        colors = ButtonDefaults.buttonColors(containerColor = Color.White)

    ) {

        Text(

            text = buttonName,

            color = Color(0xFF253555),

            style = textStyle3.copy(textAlign = TextAlign.Center)

        )

    }

}