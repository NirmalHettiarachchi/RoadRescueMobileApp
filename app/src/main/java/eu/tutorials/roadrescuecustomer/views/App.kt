package eu.tutorials.roadrescuecustomer.views

import CustomerSupportTicketViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import eu.tutorials.roadrescuecustomer.viewmodels.NavigationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.RegisterViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationViewModel: LocationViewModel,
    profileViewModel: ProfileViewModel,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    customerSupportTicketViewModel: CustomerSupportTicketViewModel,
    navigationViewModel: NavigationViewModel,
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
    val navigationToInstructionsScreen = { navController.navigate("instructionsscreen") }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun shouldShowAppBarAndFooter(route: String?): Boolean {
        return route !in listOf("loginscreen", "signupscreen")
    }

    var loading by remember { mutableStateOf(false) }

    CircularProgressBar(isDisplayed = loading)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if(shouldShowAppBarAndFooter(currentRoute)) {
                ModalDrawerSheet(
                    content = {
                        SidebarContent({isLogOut ->
                            scope.launch {
                                drawerState.close()
                                if(isLogOut){
                                    loading = true
                                    delay(2000)
                                    loading = false
                                    AppPreferences(context).clearAllPreferences()
                                    navController.navigate("loginscreen") {
                                        popUpTo("loginscreen") { inclusive = true }
                                    }
                                }
                            }

                        }, navController, context, currentStateViewModel, serviceRequestViewModel, navigationViewModel)
                    }
                )
            }
        }) {
        Scaffold(
            topBar = {
                if(shouldShowAppBarAndFooter(currentRoute)) {
                    Header { scope.launch { drawerState.open() } }
                } else {
                    AuthHeader()
                }
            },
            bottomBar = {
                if (shouldShowAppBarAndFooter(currentRoute)) {
                    Footer(
                        navigationToDashboardScreen = navigationToDashboardScreen,
                        navigationToProfileScreen = navigationToProfileScreen,
                        navigationToTrackLocationScreen = navigationToTrackLocationScreen,
                        navigationToActivitiesScreen = navigationToActivitiesScreen,
                        navigationToInstructionsScreen = navigationToInstructionsScreen,
                        navigationViewModel = navigationViewModel
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
                        navigationViewModel = navigationViewModel,
                        context = context,
                        navController = navController
                    )
                }
                composable("loginscreen") {
                    LoginScreen(navController, context, loginViewModel)
                }
                composable("signupscreen") {
                    RegisterScreen(navController, context, registerViewModel, loginViewModel)
                }
                composable("profilescreen") {
                    ProfileScreen(
                        profileViewModel = profileViewModel,
                        serviceRequestViewModel = serviceRequestViewModel
                    )
                }
                composable("tracklocationscreen") {
                    TrackLocationScreen(
                        currentStateViewModel,
                        context
                    )
                }
                composable("activitiesscreen") {
                    ActivitiesScreen(serviceRequestViewModel)
                }
                composable("helpscreen") {
                    HelpScreen(customerSupportTicketViewModel)
                }
                composable("settingsscreen") {
                    SettingsScreen(loginViewModel, context, profileViewModel, navController, currentStateViewModel, serviceRequestViewModel)
                }
                composable("instructionsscreen") {
                    InstructionsScreen()
                }
            }
        }
    }
}