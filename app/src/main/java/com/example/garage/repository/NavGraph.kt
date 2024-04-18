package com.example.garage.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.garage.models.GarageTechnician
import com.example.garage.models.LocationUtils
import com.example.garage.viewModels.GarageActivityDetails
import com.example.garage.viewModels.GarageSharedViewModel
import com.example.garage.viewModels.LocationViewModel
import com.example.garage.viewModels.SharedViewModel
import com.example.garage.views.Activities
import com.example.garage.views.AddTechnician
import com.example.garage.views.EditTechnician
import com.example.garage.views.GarageDashboard
import com.example.garage.views.GarageProfile
import com.example.garage.views.GarageProfileEdit
import com.example.garage.views.LoginScreen
import com.example.garage.views.RegisterScreen
import com.example.garage.views.TechnicianApp.TechnicianCompleteJob
import com.example.garage.views.TechnicianApp.TechnicianDashboard
import com.example.garage.views.TechnicianProfile
import com.example.garage.views.TechniciansList
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    val context= LocalContext.current
    val sharedViewModel: SharedViewModel = viewModel()
    val garageSharedViewModel:GarageSharedViewModel= viewModel()
    val locationViewModel:LocationViewModel= viewModel()
    val locationUtils=LocationUtils(context)

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



        composable(
            route=Screen.Login.route
        ){
            LoginScreen(navController=navController)
        }

        composable(
            route=Screen.Register.route
        ){
            RegisterScreen(navHostController = navController)
        }


        composable(
            route=Screen.GarageDashboard.route
        ){

            GarageDashboard(navController=navController,navStatus="tempData",garageSharedViewModel= garageSharedViewModel)
        }
        
        composable(
            route="${Screen.Activities.route}/${GarageTechnician().getTechId()}/" +
                    "${GarageTechnician().getTechFirstName()}/${GarageTechnician().getTechLastName()}"
        ){
            Activities(activityDetails = GarageActivityDetails(currentTime,currentDate, "Gayan","Axio 2017",
                "T-002",3000f,"I need to replace a tire on my car","Thiran Sasanka") ,navController, "activities",sharedViewModel = sharedViewModel)
        }

        composable(
            route=Screen.EditTechnician.route,
        ){
            EditTechnician(navController = navController,navyStatus = "editTechnician",sharedViewModel = sharedViewModel)
        }

        composable(
            route=Screen.AddTechnician.route
        ){
            AddTechnician(navController = navController, navyStatus = "addTechnician")
        }

        composable(
            route=Screen.GarageProfile.route
        ){

            val result=navController.previousBackStackEntry?.savedStateHandle?.get<GarageCommonDetails>("garageDetails")



            GarageProfile(
                garageId=result?.garageId,
                garageName=result?.garageName,
                garageContactNumber=result?.garageContactNumber,
                garageStatus=result?.garageStatus,
                garageEmail=result?.garageEmail,
                garageRating=result?.garageRating,
                garageType=result?.garageType,
                garageOwner=result?.garageOwner,
                garageProfileImageRef=result?.garageProfileImageRef,
                navController = navController,
                navyStatus = "dasdewa",
                garageSharedViewModel = garageSharedViewModel
            )
        }

        composable(
            route=Screen.GarageProfileEdit.route
        ){
            GarageProfileEdit(navController = navController,"garageProfileEdit",garageSharedViewModel = garageSharedViewModel)
        }

        composable(
            route=Screen.TechnicianList.route
        ){
            TechniciansList(navController = navController, navyStatus = "technicianList",sharedViewModel = sharedViewModel)
        }

        composable(
            route=Screen.TechnicianProfile.route
        ){
            TechnicianProfile(navController = navController, navyStatus = "technicianProfile",sharedViewModel = sharedViewModel)
        }

        composable(
            route=Screen.TechnicianDashboard.route
        ){
            TechnicianDashboard(navController = navController, navStatus = "technicianDashboard")
        }

        composable(
            route=Screen.TechnicianCompleteJob.route
        ){
            TechnicianCompleteJob(navController = navController, navStatus = "technicianCompleteJob", locationUtils = locationUtils, locationViewModel = locationViewModel,context=context)
        }

    }
}

