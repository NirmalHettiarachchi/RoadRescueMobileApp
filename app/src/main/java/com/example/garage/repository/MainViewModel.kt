package com.example.garage.repository

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garage.models.BackendResponse
import com.example.garage.models.ResponseState
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    private val _backendState = mutableStateOf(ResponseState())
    val backendState: State<ResponseState> = _backendState


    fun fetchBackend() {
        viewModelScope.launch {
//            try {
//                val call= garageService.getData()
//                call.enqueue(object :Callback<BackendResponse>{
//                    override fun onResponse(
//                        call: Call<BackendResponse>, response: Response<BackendResponse>,
//                    ) {
//                        if (response.isSuccessful){
//                            Log.d("Successfully","response successfully")
//                            _backendState.value=_backendState.value.copy(
//                                loading = false,
//                                error = null,
//                                response = response.body()?.data
//
//                            )
//                        }else{
//                            Log.d("Unsuccessfully","response is not successfully")
//                            _backendState.value=_backendState.value.copy(
//                                loading = false,
//                                error = response.body()?.message,
//                                response = null
//
//                            )
//                        }
//                    }
//
//                    override fun onFailure(call: Call<BackendResponse>, t: Throwable) {
//                        Log.d("onFailure","response onFailure")
//                        _backendState.value=_backendState.value.copy(
//                            loading = false,
//                            error = "Error (on failure function ) : ${t.message}"
//
//                        )
//                    }

                // call get data function to get data from backend
                garageService.getData().enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>,
                    ) {
                        if (response.code() == 200) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status")
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                Log.d("TAG", status)
                                Log.d("TAG", message)
                                Log.d("TAG", data)
                            }
                        } else if (response.code() == 202) {
                            // TODO Handle
                        } else if (response.code() == 401) {
                            // TODO Handle
                        } else {
                            Log.d("TAG", "Went wrong")
                            Log.d("TAG", response.code().toString())
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                        // Handle failure
                        Log.d("TAG", "An error occurred: $t")
                    }
                })

//                })

//            }catch (e:Exception){
//                _backendState.value=_backendState.value.copy(
//                    loading = false,
//                    error = "Error (catch function ): ${e.message}"
//
//                )
//            }

//            }
        }
    }

}