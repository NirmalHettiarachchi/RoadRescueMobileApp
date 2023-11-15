package eu.tutorials.roadrescuecustomer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tutorials.roadrescuecustomer.ui.theme.RoadRescueCustomerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentStateViewModel: CurrentStateViewModel = viewModel()
            val serviceRequestViewModel: ServiceRequestViewModel = viewModel()
            RoadRescueCustomerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(currentStateViewModel, serviceRequestViewModel)
                }
            }
        }
    }
}

