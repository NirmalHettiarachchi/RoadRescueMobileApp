package com.example.garage.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel :ViewModel() {


    private val _backendState= mutableStateOf(GarageState())
    val backendState:State<GarageState> = _backendState


    /*init {
        fetchBackend()
    }*/

     fun fetchBackend(){
        viewModelScope.launch {
            try {
                val response= garageService.getData()
                _backendState.value=_backendState.value.copy(
                    loading = false,
                    response=response,
                    error = null

                )

            }catch (e:Exception){
                _backendState.value=_backendState.value.copy(
                    loading =false,
                    error = "Error : ${e.message}"

                )
            }
        }
    }

    data class GarageState(
        val loading:Boolean=true,
        val response:String?=null,
        val error:String?=null
    )
}