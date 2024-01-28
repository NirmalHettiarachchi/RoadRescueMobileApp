package com.example.garage.views


import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.net.Uri
import android.os.Build


import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.garage.ui.theme.GarageTheme
import com.example.garage.viewModels.GarageActivityDetails
import com.example.garage.viewModels.GarageDashboardViewModel
import com.example.garage.viewModels.GarageProfileViewModel
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

    lateinit var navController:NavController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            GarageTheme {

                navController= rememberNavController()
                SetupNavGraph(navController = navController as NavHostController)




                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                   // Header()


//                    Footer()
                  //HelpBox()
                    //SidebarContent()



                   *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



                        val technicians = listOf<String>("Saman Kumara","Tharindu Dakshina","Ajith Muthukumara","Namal Rajapakasha")

                        GarageDashboard(
                           garageDetails = garageDashboardViewModel, technicianList = technicians
                        )

                    }*//*





                    *//*GarageProfile(
                        GarageProfileViewModel("Nirmal","C-001","Thiran Sasanka",
                            "+94761339805", "tharindudakshina@gmail.com"
                        )
                    )*//*


                   // garageProfileEdit()

                   // GridWithTwoRows()

                    val date = SimpleDateFormat("dd/M/yyyy")
                    val time = SimpleDateFormat("hh:mm")
                    val currentDate = date.format(Date())
                    val currentTime = time.format(Date())



                    *//*Activities(
                        GarageActivityDetails(currentTime,currentDate, "Gayan","Axio 2017",
                            "T-002",3000f,"I need to replace a tire on my car","Thiran Sasanka")
                    )*//*

                   // TechniciansList()

                  //  AddTechnician()

                  //  TechnicianProfile()

                  //  EditTechnician()

                   // Message()

                }*/

            }
        }
    }

}




