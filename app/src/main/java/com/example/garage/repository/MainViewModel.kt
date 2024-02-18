package com.example.garage.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garage.models.BackendResponse
import com.example.garage.models.ResponseState
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel :ViewModel() {


    private val _backendState= mutableStateOf(ResponseState())
    val backendState:State<ResponseState> = _backendState


     fun fetchBackend(){
        viewModelScope.launch {
            try {
                val call= garageService.getData()
                call.enqueue(object :Callback<BackendResponse>{
                    override fun onResponse(
                        call: Call<BackendResponse>,
                        response: Response<BackendResponse>,
                    ) {
                        if (response.isSuccessful){
                            _backendState.value=_backendState.value.copy(
                                loading = false,
                                error = null,
                                response = response.body()?.data

                            )
                        }else{
                            _backendState.value=_backendState.value.copy(
                                loading = false,
                                error = response.body()?.message,
                                response = null

                            )
                        }
                    }

                    override fun onFailure(call: Call<BackendResponse>, t: Throwable) {
                        _backendState.value=_backendState.value.copy(
                            loading = false,
                            error = "Error (on failure function ) : ${t.message}"

                        )
                    }

                })

            }catch (e:Exception){
                _backendState.value=_backendState.value.copy(
                    loading = false,
                    error = "Error (exception function ): ${e.message}"

                )
            }
        }
    }

}