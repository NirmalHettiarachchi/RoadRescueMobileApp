package com.example.garage.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.garage.repository.GarageCommonDetails
import com.example.garage.repository.TechData

class SharedViewModel: ViewModel(){
    var technician by mutableStateOf<TechData?>(null)
        private set

    var garage by mutableStateOf<GarageCommonDetails?>(null)
        private set

    fun techData(techData: TechData){
        technician = techData
    }

    fun garageCommonDetails(garageCommonDetails: GarageCommonDetails){
        garage = garageCommonDetails
    }
}