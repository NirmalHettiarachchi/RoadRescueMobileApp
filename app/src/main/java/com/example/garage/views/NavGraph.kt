package com.example.garage.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.garage.repository.Screen
import com.example.garage.viewModels.GarageActivityDetails
import com.example.garage.viewModels.GarageDashboardViewModel
import com.example.garage.viewModels.GarageProfileViewModel
import java.text.SimpleDateFormat
import java.time.Period
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.GarageDashboard.route,
        enterTransition = {
            fadeIn(animationSpec = tween(700))+slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(700))+slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        }
    ){
        val date = SimpleDateFormat("dd/M/yyyy")
        val time = SimpleDateFormat("hh:mm")
        val currentDate = date.format(Date())
        val currentTime = time.format(Date())


        val technicians = listOf<String>("Saman Kumara","Tharindu Dakshina","Ajith Muthukumara","Namal Rajapakasha")

        composable(
            route=Screen.GarageDashboard.route
        ){


            val garageDashboardViewModel = GarageDashboardViewModel(
                "Nirmal Dakshina", Period.of(1, 2, 3),
                "Tire Punch", "Need help as soon as possible", 25000.00
            )


            GarageDashboard(garageDetails = garageDashboardViewModel, technicianList = technicians,navController,navStatus="garageProfile")
        }
        
        composable(
            route=Screen.Activities.route
        ){
            Activities(activityDetails = GarageActivityDetails(currentTime,currentDate, "Gayan","Axio 2017",
                "T-002",3000f,"I need to replace a tire on my car","Thiran Sasanka") ,navController, "activities")
        }

        composable(
            route=Screen.EditTechnician.route
        ){
            EditTechnician(navController = navController, navyStatus = "editTechnician")
        }

        composable(
            route=Screen.AddTechnician.route
        ){
            AddTechnician(navController = navController, navyStatus = "addTechnician")
        }

        composable(
            route=Screen.GarageProfile.route
        ){
            GarageProfile(
                garageProfileDetails = GarageProfileViewModel("Nirmal","C-001","Thiran Sasanka", "+94761339805", "tharindudakshina@gmail.com"),
                navController = navController,
                navyStatus = "garageProfile"
            )
        }

        composable(
            route=Screen.GarageProfileEdit.route
        ){
            GarageProfileEdit(navController = navController,"garageProfileEdit")
        }

        composable(
            route=Screen.TechnicianList.route
        ){
            TechniciansList(navController = navController, navyStatus = "technicianList")
        }

        composable(
            route=Screen.TechnicianProfile.route
        ){
            TechnicianProfile(navController = navController, navyStatus = "technicianProfile")
        }
    }
}

