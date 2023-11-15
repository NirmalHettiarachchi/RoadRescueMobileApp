package eu.tutorials.roadrescuecustomer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tutorials.roadrescuecustomer.ui.theme.RoadRescueCustomerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoadRescueCustomerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "dashboardscreen") {
        composable("dashboardscreen") {
            DashboardScreen (
                {navController.navigate("profilescreen")},
                {navController.navigate("tracklocationscreen")}
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
                {navController.navigate("profilescreen")}
            )
        }
    }
}