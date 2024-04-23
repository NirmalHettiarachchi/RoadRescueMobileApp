package eu.tutorials.roadrescuecustomer.views

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.TechnicianLocation
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TrackLocationScreen(
    currentStateViewModel: CurrentStateViewModel,
    context: Context,
) {
    val loading by currentStateViewModel.loading
    CircularProgressBar(isDisplayed = loading)

//    LaunchedEffect(key1 = true) {
//        while (true) {
//            currentStateViewModel.fetchLatestRequest(
//                AppPreferences(context).getStringPreference(
//                    "CUSTOMER_ID",
//                    ""
//                ),
//                showLoading = false
//            )
//            isChangeAutomatic = true
//            delay(10000)
//        }
//    }

    LaunchedEffect(key1 = true) {
        currentStateViewModel.fetchLatestRequest(
            AppPreferences(context).getStringPreference(
                "CUSTOMER_ID",
                ""
            ),
            showLoading = true
        )
        currentStateViewModel.fetchLatestRequestTechLocation(
            AppPreferences(context).getStringPreference(
                "CUSTOMER_ID",
                ""
            ),
            showLoading = true
        )
    }

    LaunchedEffect(key1 = true) {
        while(true) {
            currentStateViewModel.fetchLatestRequestTechLocation(
                AppPreferences(context).getStringPreference(
                    "CUSTOMER_ID",
                    ""
                ),
                showLoading = false
            )
            delay(5000)
        }
    }

    val request = currentStateViewModel.latestRequests.collectAsState().value.firstOrNull()
    val techLocation = currentStateViewModel.techLocationLatestRequests.collectAsState().value.firstOrNull()

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
            if (request?.status?.toInt() == 2 || request?.status?.toInt() == 3 || request?.status?.toInt() == 4) {
                PendingActivityTrackLocationScreen(
                    request.location,
                    request.serviceProviderName,
                    request.serviceProviderPhoneNum,
                    request.serviceProviderLocation,
                    techLocation?.location,
                    LocalContext.current
                )
            } else {
                NoPendingActivityTrackLocationScreen()
            }
        }
    }
}

@Composable
fun NoPendingActivityTrackLocationScreen() {
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
                text = "There are no pending ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Text(
                text = "service requests to track!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@Composable
fun PendingActivityTrackLocationScreen(
    latLong: String,
    serviceProviderName: String,
    serviceProviderPhoneNum: String?,
    serviceProviderLocation: String?,
    technicianLocation: String?,
    context: Context
) {
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
                text = "A technician from $serviceProviderName is ",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Text(
                text = "on the way to your location . . .",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(20.dp))
            //displayLocation
            LocationDisplay(
                latLong,
                serviceProviderLocation,
                technicianLocation,
                modifier = Modifier
                    .border(width = 2.dp, color = Color.White)
                    .size(320.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 8.dp)
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
                    text = "Contact Service Provider",
                    style = textStyle3
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LocationDisplay(
    latLongStr: String,
    latLongSpStr: String?,
    latLongTechStr: String?,
    modifier: Modifier
) {
    val mapReady = remember { mutableStateOf(false) }

    val latitude = extractLatitude(latLongStr)
    val longitude = extractLongitude(latLongStr)

    var latitudeTech = 0.00
    var longitudeTech = 0.00
//    val latitudeTech = 6.994833270682517
//    val longitudeTech = 79.87625012425737

    var latitudeSp = 0.00
    var longitudeSp = 0.00

    if(latLongSpStr != null) {
        latitudeSp = extractLatitudeSp(latLongSpStr)
        longitudeSp = extractLongitudeSp(latLongSpStr)
    }

    if(latLongTechStr != null) {
        latitudeTech = extractLatitudeSp(latLongTechStr)
        longitudeTech = extractLongitudeSp(latLongTechStr)
    }

    if (latitude != null && longitude != null) {
        val curLocation = LatLng(latitude, longitude)
        val techLocation = LatLng(latitudeTech, longitudeTech)
        val spLocation = LatLng(latitudeSp, longitudeSp)

        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(curLocation, 10f)
        }
        Box(
            modifier = modifier
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition,
                onMapLoaded = { mapReady.value = true }
            ) {
                if (mapReady.value) {
                    val techIconBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.tech_icon)
                    val resizedTechIcon = resizeBitmap(techIconBitmap, 90, 90)
                    val techIcon = BitmapDescriptorFactory.fromBitmap(resizedTechIcon)

                    val spIconBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.garage)
                    val resizedSpIcon = resizeBitmap(spIconBitmap, 90, 90)
                    val spIcon = BitmapDescriptorFactory.fromBitmap(resizedSpIcon)
                    Marker(
                        state = MarkerState(position = curLocation),
                        title = "Your location"
                    )
                    Marker(
                        state = MarkerState(position = techLocation),
                        title = "Technician's location",
                        icon = techIcon
                    )
                    Marker(
                        state = MarkerState(position = spLocation),
                        title = "Service provider's location",
                        icon = spIcon
                    )
                }
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
}

fun extractLatitudeSp(coordString: String): Double {
    val coords = coordString.split(",")
    return coords[0].trim().toDouble()
}

fun extractLongitudeSp(coordString: String): Double {
    val coords = coordString.split(",")
    return coords[1].trim().toDouble()
}

fun resizeBitmap(source: Bitmap, width: Int, height: Int): Bitmap {
    return Bitmap.createScaledBitmap(source, width, height, false)
}