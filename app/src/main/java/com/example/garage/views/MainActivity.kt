package com.example.garage.views


import android.net.Uri
import android.os.Build


import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.garage.ui.theme.GarageTheme
import com.example.garage.viewModels.GarageActivityDetails
import com.example.garage.viewModels.GarageDashboardViewModel
import java.text.SimpleDateFormat
import java.time.Period
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private var selectedImageUri by mutableStateOf<Uri?>(null)
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//            window.statusBarColor=getColor(R.color.purple_700)
//            window.navigationBarColor=getColor(R.color.purple_700)
            GarageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                   // Header()


//                    Footer()
                  //HelpBox()
                    //SidebarContent()



                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        val garageDashboardViewModel = GarageDashboardViewModel(
                            "Nirmal Dakshina", Period.of(1, 2, 3),
                            "Tire Punch", "Need help as soon as possible", 25000.00
                        )

                        val technicians = listOf<String>("Saman Kumara","Tharindu Dakshina","Ajith Muthukumara","Namal Rajapakasha")

                        GarageDashboard(
                           garageDetails = garageDashboardViewModel, technicianList = technicians
                        )

                    }*/





                    /*GarageProfile(
                        GarageProfileViewModel("Nirmal","C-001","Thiran Sasanka",
                            "+94761339805", "tharindudakshina@gmail.com"
                        )
                    )*/


                   // garageProfileEdit()

                   // GridWithTwoRows()

                    val date = SimpleDateFormat("dd/M/yyyy")
                    val time = SimpleDateFormat("hh:mm")
                    val currentDate = date.format(Date())
                    val currentTime = time.format(Date())



                    /*Activities(
                        GarageActivityDetails(currentTime,currentDate, "Gayan","Axio 2017",
                            "T-002",3000f,"I need to replace a tire on my car","Thiran Sasanka")
                    )*/

                   // TechniciansList()

                    RegisterTechnician()

                }
            }
        }
    }

}




