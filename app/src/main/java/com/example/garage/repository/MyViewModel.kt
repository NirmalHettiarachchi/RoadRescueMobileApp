package com.example.garage.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MyViewModel: ViewModel() {

    private val _response= MutableStateFlow<String?>(null)
    val response :StateFlow<String?> = _response.asStateFlow()


    fun fetchData(){
        viewModelScope.launch {
            try {
                val data = sendRequest("http://192.168.56.1:8082/RoadRescueBackend/garage")
                _response.value=data
            }catch (e:IOException){
                _response.value="Error : ${e.message}"
            }
        }
    }
}