package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.models.LocationUtils
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LoginViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun App(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationViewModel: LocationViewModel,
    profileViewModel: ProfileViewModel,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    context: MainActivity
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val locationUtils = LocationUtils(context)

    val navigationToDashboardScreen = { navController.navigate("dashboardscreen") }
    val navigationToProfileScreen = { navController.navigate("profilescreen") }
    val navigationToTrackLocationScreen = { navController.navigate("trackLocationscreen") }
    val navigationToActivitiesScreen = { navController.navigate("activitiesscreen") }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun shouldShowAppBarAndFooter(route: String?): Boolean {
        return route !in listOf("loginscreen", "signupscreen")
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if(shouldShowAppBarAndFooter(currentRoute)) {
                ModalDrawerSheet(
                    content = {
                        SidebarContent({
                            scope.launch {
                                drawerState.close()
                            }
                        }, navController, context)
                    }
                )
            }
        }) {
        Scaffold(
            topBar = {
                if(shouldShowAppBarAndFooter(currentRoute)) {
                    Header { scope.launch { drawerState.open() } }
                }
            },
            bottomBar = {
                if (shouldShowAppBarAndFooter(currentRoute)) {
                    Footer(
                        navigationToDashboardScreen = navigationToDashboardScreen,
                        navigationToProfileScreen = navigationToProfileScreen,
                        navigationToTrackLocationScreen = navigationToTrackLocationScreen,
                        navigationToActivitiesScreen = navigationToActivitiesScreen
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = if (AppPreferences(context).getStringPreference(
                        "NAME",
                        ""
                    ).isNotEmpty()
                ) "dashboardscreen"
                else "loginscreen",
                Modifier.padding(innerPadding)
            ) {
                composable("dashboardscreen") {
                    DashboardScreen(
                        currentStateViewModel = currentStateViewModel,
                        serviceRequestViewModel = serviceRequestViewModel,
                        locationUtils = locationUtils,
                        locationViewModel = locationViewModel,
                        context = context,
                    )
                }
                composable("loginscreen") {
                    LoginScreen(navController, context, loginViewModel)
                }
                composable("signupscreen") {
                    RegisterScreen(navController, context, registerViewModel)
                }
                composable("profilescreen") {
                    ProfileScreen(
                        profileViewModel = profileViewModel,
                    )
                }
                composable("tracklocationscreen") {
                    TrackLocationScreen(
                        currentStateViewModel,
                        locationViewModel,
                    )
                }
                composable("activitiesscreen") {
                    ActivitiesScreen()
                }
                composable("helpscreen") {
                    HelpScreen()
                }
                composable("settingsscreen") {
                    SettingsScreen()
                }
            }
        }
    }
}