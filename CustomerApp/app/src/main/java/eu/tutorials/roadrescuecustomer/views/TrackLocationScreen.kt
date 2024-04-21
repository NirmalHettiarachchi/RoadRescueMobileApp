package com.example.garage.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
=======
import androidx.compose.foundation.shape.RoundedCornerShape
>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
import androidx.navigation.NavHostController
import com.google.maps.android.compose.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import kotlinx.coroutines.launch

@Composable
fun TrackLocationScreen(
    currentStateViewModel: CurrentStateViewModel,
    context: Context,
) {

    LaunchedEffect(key1 = true) {
        currentStateViewModel.fetchLatestRequest(
            AppPreferences(context).getStringPreference(
                "CUSTOMER_ID",
                ""
            ),
            showLoading = true
        )
    }

    val request = currentStateViewModel.latestRequests.collectAsState().value.firstOrNull()

    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Track Location",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle1
            )
            if (request?.status?.toInt() == 2) {
                PendingActivityTrackLocationScreen(
                    request.location,
                    request.serviceProviderName,
                    request.serviceProviderPhoneNum,
                    LocalContext.current
                )
            } else {
                NoPendingActivityTrackLocationScreen()
=======
import androidx.lifecycle.ViewModel

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
>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
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
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
                text = "There are no pending ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Text(
                text = "service requests to track!",
=======
                text = "No any pending sever request !",
>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )

        }
    }
}

@Composable
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
fun PendingActivityTrackLocationScreen(
    latLong: String,
    serviceProviderName: String,
    serviceProviderPhoneNum: String?,
    context: Context
) {
=======
fun pendingActivityTrackLocationScreen(
    //locationViewModel: LocationViewModel
){
>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
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
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
                text = "A technician from $serviceProviderName is ",
=======
                text = "A technician from Tech Garage is",
>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )

            Text(
                text = "on the way to your location . . .",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(16.dp))

            //displayLocation
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
            LocationDisplay(
                latLong,
=======
            /*LocationDisplay(
                locationViewModel = locationViewModel,
>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
                modifier = Modifier
                    .border(width = 2.dp, color = Color.White)
                    .size(320.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 8.dp)
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$serviceProviderPhoneNum")
                    context.startActivity(intent)
                          },
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
=======
            )*/
>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
            Spacer(modifier = Modifier.height(16.dp))


        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}




@Composable
fun LocationDisplay(
<<<<<<< HEAD:CustomerApp/app/src/main/java/eu/tutorials/roadrescuecustomer/views/TrackLocationScreen.kt
    latLongStr: String,
    modifier: Modifier
) {
    val latitude = extractLatitude(latLongStr)
    val longitude = extractLongitude(latLongStr)

    if (latitude != null && longitude != null) {
//        Text("Location: ${location.latitude} ${location.longitude}")
//        Text("$latitude $longitude")
        val curLocation = LatLng(latitude, longitude)

        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(curLocation, 15f)
        }
        Box(
            modifier = modifier
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(), // Ensure the map fills the entire Box
                cameraPositionState = cameraPosition
            ) {
                Marker(
                    state = MarkerState(position = curLocation),
                    title = "Your Location"
                )
            }
        }

    } else {
        //
    }
}

fun extractLatitude(input: String): Double? {
    val pattern = "latitude=([\\d.+-]+)".toRegex()
    val matchResult = pattern.find(input)
    return matchResult?.groupValues?.get(1)?.toDouble()
}

fun extractLongitude(input: String): Double? {
    val pattern = "longitude=([\\d.+-]+)".toRegex()
    val matchResult = pattern.find(input)
    return matchResult?.groupValues?.get(1)?.toDouble()
=======
    locationViewModel: ViewModel,
    modifier: Modifier
){

>>>>>>> 12d934a82ad8ead52339348246aadb3a9dfa091f:app/src/main/java/com/example/garage/views/TrackLocationScreen.kt
}