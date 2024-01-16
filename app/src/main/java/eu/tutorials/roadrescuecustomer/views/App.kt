package eu.tutorials.roadrescuecustomer.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.roadrescuecustomer.AppPreferences
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
    profileViewModel: ProfileViewModel,
    context: MainActivity
) {
    val navController = rememberNavController()

    val locationUtils = LocationUtils(context)

    NavHost(
        navController = navController,
        startDestination = if (AppPreferences(context).getStringPreference(
                "NAME",
                ""
            ).isNotEmpty()
        ) "dashboardscreen"
        else "loginscreen"
    ) {
        composable("dashboardscreen") {
            DashboardScreen(
                { navController.navigate("profilescreen") },
                { navController.navigate("tracklocationscreen") },
                { navController.navigate("activitiesscreen") },
                currentStateViewModel = currentStateViewModel,
                serviceRequestViewModel = serviceRequestViewModel,
                locationUtils = locationUtils,
                locationViewModel = locationViewModel,
                context = context,
                profileViewModel = profileViewModel,
                navController,
                context
            )
        }
        composable("loginscreen") {
            LoginScreen(navController, context)
        }
        composable("signupscreen") {
            SingupScreen(navController, context)
        }
        composable("profilescreen") {
            ProfileScreen(
                { navController.navigate("dashboardscreen") },
                { navController.navigate("tracklocationscreen") },
                { navController.navigate("activitiesscreen") },
                profileViewModel = profileViewModel,
                navController,
                context
            )
        }
        composable("tracklocationscreen") {
            TrackLocationScreen(
                { navController.navigate("dashboardscreen") },
                { navController.navigate("profilescreen") },
                { navController.navigate("activitiesscreen")},
                currentStateViewModel,
                locationViewModel,
                navController,
                context
            )
        } 
        composable("activitiesscreen") {
            ActivitiesScreen(
                navigationToDashboardScreen = { navController.navigate("dashboardscreen") },
                navigationToTrackLocationScreen = { navController.navigate("tracklocationscreen") },
                navigationToProfileScreen = { navController.navigate("profilescreen") },
                navHostController = navController,
                context = context
            )
        }
    }
}