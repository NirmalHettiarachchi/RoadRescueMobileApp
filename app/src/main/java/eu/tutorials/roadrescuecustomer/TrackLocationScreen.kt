package eu.tutorials.roadrescuecustomer

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.launch

@Composable
fun TrackLocationScreen(
    navigationToDashboardScreen: () -> Unit,
    navigationToProfileScreen: () -> Unit,
    currentStateViewModel: CurrentStateViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    SidebarContent {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    ) {
        Scaffold {
            Column(
                backgroundModifier.padding(it),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Header {
                        scope.launch {drawerState.open()}
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Track Location",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = textStyle1
                    )
                    if(!currentStateViewModel.isServiceRequested.value) {
                        NoPendingActivityTrackLocationScreen()
                    } else {
                        PendingActivityTrackLocationScreen(
                            locationUtils,
                            locationViewModel,
                            context
                        )
                    }
                    HelpBox()
                }
                Footer(navigationToDashboardScreen, navigationToProfileScreen) {}
            }
        }
    }
}

@Composable
fun NoPendingActivityTrackLocationScreen(){
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
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "No any pending service requests!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@Composable
fun PendingActivityTrackLocationScreen(
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    //todo
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
                text = "A technician from Tech Garage is ",
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
            LocationDisplay(
                locationUtils = locationUtils,
                locationViewModel = locationViewModel,
                context = context
            )
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
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    val location = locationViewModel.location.value

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                //Have access to location
                locationUtils.requestLocationUpdates(locationViewModel = locationViewModel)
            } else {
                val rationalRequired = ActivityCompat
                    .shouldShowRequestPermissionRationale(
                        context as MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) || ActivityCompat
                    .shouldShowRequestPermissionRationale(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )

                if(rationalRequired) {
                    Toast.makeText(
                        context,
                        "Location permission is required for this feature to work.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Location permission is required. Please enable it from the system settings.",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    )
    if(location != null) {
        Text("Location: ${location.latitude} ${location.longitude}")
    } else {
        Text(text = "Location not available")
    }

    if(locationUtils.hasLocationPermission(context)) {
        locationUtils.requestLocationUpdates(locationViewModel)
    } else {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}