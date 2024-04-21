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
import androidx.navigation.NavController
import com.example.garage.repository.Screen
import kotlin.system.exitProcess


@Composable

fun SidebarContent(
    navController: NavController,
    menuClicked:()->Unit
){


    Column(


        modifier = Modifier

            .fillMaxHeight()

            .width(250.dp)

            .background(Color(0xFF253555))

    ) {

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

            SidebarButton(buttonName="Activities",verticalPadding=8, onClick = {
                navController.navigate(Screen.Activities.route)
            })

            SidebarButton(buttonName="Help",verticalPadding=8, onClick = {
                navController.navigate(Screen.HelpScreen.route)
            })

            SidebarButton(buttonName="Settings",verticalPadding=8, onClick = {
                navController.navigate(Screen.SettingsScreen.route)
            })

        }

        SidebarButton(buttonName="Log Out",verticalPadding=16, onClick = {

            // Handle logout logic here
            // For example, clear session data and navigate to login screen
//            clearSessionData()
//            navigateToLoginScreen()

            // Stop the application execution
            exitProcess(0)
        })
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