package eu.tutorials.roadrescuecustomer.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.roadrescuecustomer.models.LocationUtils
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel

@Composable
fun App(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationViewModel: LocationViewModel,
    profileViewModel: ProfileViewModel
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
                context = context,
                profileViewModel = profileViewModel
            )
        }
        composable("profilescreen") {
            ProfileScreen (
                {navController.navigate("dashboardscreen")},
                {navController.navigate("tracklocationscreen")},
                profileViewModel = profileViewModel
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