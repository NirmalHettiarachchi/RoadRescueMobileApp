package com.example.garage.views.TechnicianApp

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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.garage.repository.Screen
import com.example.garage.views.SidebarButton

@Composable
fun TechnicianSliderContent(
    navController: NavController,
    menuClicked:()->Unit
) {
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
            var garageOpenClosedStatus by remember { mutableStateOf("Deactivate") }

            SidebarButton(buttonName=garageOpenClosedStatus,verticalPadding=8, onClick = {
                garageOpenClosedStatus= chaneTechnicianState(garageOpenClosedStatus)
            })

            SidebarButton(buttonName="Activities",verticalPadding=8, onClick = {
                navController.navigate(Screen.TechnicianActivities.route)
            })

            SidebarButton(buttonName="Help",verticalPadding=8, onClick = {
                //navController.navigate(Screen.HelpScreen.route)
            })

            SidebarButton(buttonName="Settings",verticalPadding=8, onClick = {
                //navController.navigate(Screen.SettingsScreen.route)
            })

        }

        SidebarButton(buttonName="Log Out",verticalPadding=16, onClick = {
            navController.navigate(Screen.Login.route)
        })
    }

}

fun chaneTechnicianState(garageOpenClosedStatus: String):String {
    var stastus=""
    if (garageOpenClosedStatus == "Deactivate") {
        stastus="Activate"
    }else{
        stastus="Deactivate"
    }
    return stastus
}