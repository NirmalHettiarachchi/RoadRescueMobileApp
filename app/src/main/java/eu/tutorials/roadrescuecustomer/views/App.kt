package eu.tutorials.roadrescuecustomer

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationViewModel: LocationViewModel
) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(navController = navController, startDestination = "dashboardscreen") {
        composable("dashboardscreen") {
            DashboardScreen (
                {navController.navigate("profilescreen")},
                {navController.navigate("tracklocationscreen")},
                currentStateViewModel = currentStateViewModel,
                serviceRequestViewModel = serviceRequestViewModel,
                locationUtils = locationUtils,
                locationViewModel = locationViewModel,
                context = context
            )
        }
        composable("profilescreen") {
            ProfileScreen (
                {navController.navigate("dashboardscreen")},
                {navController.navigate("tracklocationscreen")}
            )
        }
        composable("tracklocationscreen") {
            TrackLocationScreen (
                {navController.navigate("dashboardscreen")},
                {navController.navigate("profilescreen")},
                currentStateViewModel,
                locationViewModel
            )
        }
    }
}