package eu.tutorials.roadrescuecustomer.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseApp
import eu.tutorials.roadrescuecustomer.ui.theme.RoadRescueCustomerTheme
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentStateViewModel: CurrentStateViewModel = viewModel()
            val serviceRequestViewModel: ServiceRequestViewModel = viewModel()
            val locationViewModel: LocationViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel()
            RoadRescueCustomerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(
                        currentStateViewModel,
                        serviceRequestViewModel,
                        locationViewModel,
                        profileViewModel,this
                    )
                }
            }
        }
    }
}

