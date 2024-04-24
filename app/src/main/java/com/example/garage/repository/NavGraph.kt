package com.example.garage.repository

import android.content.Context
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
import com.example.garage.models.LocationUtils
import com.example.garage.viewModels.GarageSharedViewModel
import com.example.garage.viewModels.LocationViewModel
import com.example.garage.viewModels.LoginShearedViewModel
import com.example.garage.viewModels.SharedViewModel
import com.example.garage.viewModels.TechnicianShearedViewModel
import com.example.garage.views.Activities
import com.example.garage.views.AddTechnician
import com.example.garage.views.ChangePhoneNumWindow
import com.example.garage.views.EditTechnician
import com.example.garage.views.GarageDashboard
import com.example.garage.views.GarageProfile
import com.example.garage.views.GarageProfileEdit
import com.example.garage.views.HelpBox
import com.example.garage.views.LoginScreen
import com.example.garage.views.RegisterScreen
import com.example.garage.views.SettingsScreen
import com.example.garage.views.TechnicianApp.TechnicianCompleteJob
import com.example.garage.views.TechnicianApp.TechnicianDashboard
import com.example.garage.views.TechnicianApp.TechnicianProfile
import com.example.garage.views.TechniciansList
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    mainActivity: Context,
    navController: NavHostController
){

//    var screen:String=""
//    if (session.isLoggedIn.value){
//        screen=Screen.TechnicianDashboard.route
//    }else{
//        screen=Screen.GarageDashboard.route
//    }

    val context= LocalContext.current
    val sharedViewModel: SharedViewModel = viewModel()
    val garageSharedViewModel:GarageSharedViewModel= viewModel()
    val loginShearedViewModel: LoginShearedViewModel = viewModel()
    val technicianDashboardServiceCommonDetails:TechnicianShearedViewModel  = viewModel()
    val locationViewModel:LocationViewModel= viewModel()
    val locationUtils=LocationUtils(context)



    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
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
            LoginScreen(navController=navController,loginShearedViewModel,locationUtils,locationViewModel)
        }

        composable(
            route=Screen.Register.route
        ){
            RegisterScreen(navHostController = navController,locationUtils,locationViewModel,loginShearedViewModel)
        }


        composable(
            route=Screen.GarageDashboard.route
        ){

            GarageDashboard(navController=navController,navStatus="tempData",garageSharedViewModel= garageSharedViewModel,loginShearedViewModel)
        }
        
        composable(
            route=Screen.Activities.route
        ){
            Activities(navController, "activities")
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
            GarageProfile(
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

        composable(route=Screen.SettingsScreen.route) {
            SettingsScreen(navController)
        }

        composable(route=Screen.ChangePhoneNumberScreen.route){
            ChangePhoneNumWindow(navController = navController, onDismiss = {})
        }

        composable(route=Screen.HelpScreen.route){
            HelpBox(navController = navController, garageSharedViewModel = garageSharedViewModel)
        }

        composable(
            route=Screen.TechnicianDashboard.route
        ){
            TechnicianDashboard(navController = navController, navStatus = "technicianDashboard",loginShearedViewModel,technicianDashboardServiceCommonDetails)
        }

        composable(
            route=Screen.TechnicianCompleteJob.route
        ){
            TechnicianCompleteJob(navController = navController, navStatus = "technicianCompleteJob", locationUtils = locationUtils, locationViewModel = locationViewModel, context = context,
                technicianShearedViewModel = technicianDashboardServiceCommonDetails
            )
        }

    }
}

