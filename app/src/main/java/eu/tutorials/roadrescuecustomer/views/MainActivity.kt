package eu.tutorials.roadrescuecustomer.views

import CustomerSupportTicketViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tutorials.roadrescuecustomer.ui.theme.RoadRescueCustomerTheme
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LoginViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.NavigationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.NotificationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.RegisterViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.TipViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        setContent {
            val currentStateViewModel: CurrentStateViewModel = viewModel()
            val serviceRequestViewModel: ServiceRequestViewModel = viewModel()
            val locationViewModel: LocationViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel()
            val registerViewModel: RegisterViewModel = viewModel()
            val loginViewModel: LoginViewModel = viewModel()
            val customerSupportTicketViewModel: CustomerSupportTicketViewModel = viewModel()
            val navigationViewModel: NavigationViewModel = viewModel()
            val tipViewModel: TipViewModel = viewModel()
            val notificationViewModel: NotificationViewModel = viewModel()
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
                        profileViewModel,
                        registerViewModel,
                        loginViewModel,
                        customerSupportTicketViewModel,
                        navigationViewModel,
                        tipViewModel,
                        notificationViewModel,
                        this
                    )
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
        } else {
            Toast.makeText(this, "Location permission is required to use this app.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}

