package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

@Composable
fun TrackLocationScreen(
    navigationToDashboardScreen: () -> Unit,
    navigationToProfileScreen: () -> Unit,

){
    val drawerState= rememberDrawerState(DrawerValue.Closed)
    val scope= rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                   /* SidebarContent {
                        scope.launch { drawerState.close() }
                    }*/
                }
            )
        }
    ) {
        Scaffold{
            Column(
                backgroundModifier.padding(it),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    /*Header {
                        scope.launch { drawerState.open() }
                    }*/
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Track Location",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = textStyle1
                    )
                    if (true){
                        noPendingActivityTrackLocationScreen()
                    }else{
                        pendingActivityTrackLocationScreen(
                            //locationViewModel
                        )
                    }
                    HelpBox()
                }
              // Footer(navigationToDashboardScreen,navigationToProfileScreen)
            }
        }
    }
}


@Composable
fun noPendingActivityTrackLocationScreen(){
    Card(
        modifier= cardModifier,
        border= BorderStroke(width = 2.dp,  Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "No any pending sever request !",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}


@Composable
fun pendingActivityTrackLocationScreen(
    //locationViewModel: LocationViewModel
){
    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A technician from Tech Garage is",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )

            Text(
                text = "on the way to your location . . .",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "ETA: 14 Minutes",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(16.dp))

            //displayLocation
            /*LocationDisplay(
                locationViewModel = locationViewModel,
                modifier = Modifier
                    .border(width = 2.dp, color = Color.White)
                    .size(320.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 8.dp)
            )*/
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                border = BorderStroke(width = 2.dp, color = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
            ) {
                Text(
                    text = "Contact Technician",
                    style = textStyle3
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun LocationDisplay(
    locationViewModel: ViewModel,
    modifier: Modifier
){
    //val location =locationViewModel.location.value
}