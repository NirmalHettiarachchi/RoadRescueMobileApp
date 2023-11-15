package eu.tutorials.roadrescuecustomer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App(currentStateViewModel: CurrentStateViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboardscreen") {
        composable("dashboardscreen") {
            DashboardScreen (
                {navController.navigate("profilescreen")},
                {navController.navigate("tracklocationscreen")},
                currentStateViewModel = currentStateViewModel
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
                currentStateViewModel
            )
        }
    }
}