package com.example.garage.repository

import androidx.annotation.StringRes

sealed class Screen(val route :String,@StringRes val technician:String) {

    object GarageDashboard:Screen(route="garageDashboard_Screen","ABC")

    object Activities:Screen(route = "GarageActivities_Screen","ABC")

    object AddTechnician:Screen(route = "addTechnician_Screen","ABC")

    object EditTechnician:Screen(route = "editTechnician_Screen","ABC")

    object GarageProfile:Screen(route = "garageProfile_Screen","ABC")

    object GarageProfileEdit:Screen(route = "garageProfileEdit_Screen","ABC")

    object TechnicianProfile:Screen(route = "technicianProfile_Screen","ABC")

    object TechnicianList:Screen(route = "technicianList_Screen","ABC")


}