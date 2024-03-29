package com.example.garage.repository

sealed class Screen(val route :String) {

    object GarageDashboard:Screen(route="garageDashboard_Screen")

    object Activities:Screen(route = "GarageActivities_Screen")

    object AddTechnician:Screen(route = "addTechnician_Screen")

    object EditTechnician:Screen(route = "editTechnician_Screen")

    object GarageProfile:Screen(route = "garageProfile_Screen")

    object GarageProfileEdit:Screen(route = "garageProfileEdit_Screen")

    object TechnicianProfile:Screen(route = "technicianProfile_Screen")

    object TechnicianList:Screen(route = "technicianList_Screen")


}