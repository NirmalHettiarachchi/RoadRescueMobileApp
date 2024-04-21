package com.example.garage.views.TechnicianApp

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.garage.models.LocationUtils
import com.example.garage.viewModels.LocationViewModel
import com.example.garage.views.CommonButton
import com.example.garage.views.Header
import com.example.garage.views.SidebarContent
import com.example.garage.views.TrackLocation
import com.example.garage.views.defaultBackground
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TechnicianCompleteJob(
    navController: NavController,
    navStatus: String,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    SidebarContent(navController) {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                Header {
                    scope.launch { drawerState.open() }
                }
            },
            bottomBar = {
                TechnicianFooter(navController, navStatus)
            }
        ) {

            TrackLocation(
                locationUtils = locationUtils,
                locationViewModel = locationViewModel,
                context = context
            )


            Log.d("latLong", "${locationViewModel.location.value?.latitude}")

            Column(
                modifier = defaultBackground.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                CommonButton(
                    btnName = "Complete Job",
                    modifier = Modifier.width(150.dp),
                    onClickButton = {

                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.84f)
                        .fillMaxHeight(1f)
                        .verticalScroll(state = rememberScrollState()),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
                    border = BorderStroke(width = 2.dp, Color.White),

                    ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    ServiceRequest(
                        navController = navController,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(300.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(20.dp)
                    ) {

                        val currentLocation =
                            locationViewModel.location.value?.latitude?.let { it1 ->
                                locationViewModel.location.value?.longitude?.let { it2 ->
                                    LatLng(
                                        it1, it2
                                    )
                                }
                            }

                        val uiSettings = remember {
                            MapUiSettings(myLocationButtonEnabled = true)
                        }

                        if (currentLocation!=null) {
                            val cameraPositionState = rememberCameraPositionState {
                                position =
                                    currentLocation.let { it1 ->
                                        CameraPosition.fromLatLngZoom(
                                            it1,
                                            10f
                                        )
                                    }
                            }

                            GoogleMap(
                                modifier = Modifier.fillMaxSize(),
                                cameraPositionState = cameraPositionState,
                                uiSettings = uiSettings
                            ) {
                                currentLocation?.let { it1 -> MarkerState(position = it1) }
                                    ?.let { it2 ->
                                        Marker(
                                            state = it2,
                                            title = "Singapore",
                                            snippet = "Marker in Singapore"
                                        )
                                    }
                            }

                        }

                    }

                }

            }
        }
    }
}

