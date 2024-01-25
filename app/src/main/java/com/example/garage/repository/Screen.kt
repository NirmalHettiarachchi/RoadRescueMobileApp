package com.example.garage.repository

sealed class Screen(val route :String) {

    object GarageDashboard:Screen(route="garageDashboard_Screen")

    object Activities:Screen(route = "GarageActivities_Screen")
}