package com.example.garage.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garage.models.GarageTechnician
import com.example.garage.models.NewTechnician
import com.example.garage.models.ResponseObject
import com.example.garage.models.ResponseState
import com.example.garage.repository.garageService
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import okhttp3.internal.wait
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException

class MainViewModel : ViewModel() {


    private val _backendState = mutableStateOf(ResponseState())
    val backendState: State<ResponseState> = _backendState

    var status: Int = 0
    var message: String? = null
    var data: Any? = null


    fun fetchBackend() {
        viewModelScope.launch {
            Log.d("TAG", "HEllo Main")
            try {
                val call = garageService.getData()
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>,
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                Log.d("TAG", status.toString())
                                Log.d("TAG", message)
                                Log.d("TAG", data)

                                _backendState.value = _backendState.value.copy(
                                    loading = false,
                                    error = null,
                                    response = ResponseObject(status, message, data)
                                )


                            }

                            Log.d("Successfully", response.body().toString())

                        } else {
                            Log.d("Unsuccessfully", "response is not successfully")
                            _backendState.value = _backendState.value.copy(
                                loading = false,
                                error = ResponseObject(400, "Bad Request", "Bad Request"),
                                response = null

                            )
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("onFailure", "response onFailure")
                        _backendState.value = _backendState.value.copy(
                            loading = false,
                            error = ResponseObject(
                                505,
                                "HTTP Version Not Supported",
                                "HTTP Version Not Supported"
                            )

                        )
                    }

                })

            } catch (e: Exception) {
                _backendState.value = _backendState.value.copy(
                    loading = false,
                    error = ResponseObject(405, "Method Not Allowed", "Method Not Allowed")

                )
            }
        }
    }


    fun addTechnician(technician: GarageTechnician){

        var responseState: ResponseState? = null

        viewModelScope.launch {
            Log.d("TAG", "Hello Main")
            try {
                Log.d("TAG", "Hello Main 1")
                val call = garageService.postTechnician(
                    NewTechnician(
                        technician.getTechFirstName(),
                        technician.getTechLastName(),
                        technician.getTechContactNumber(),
                        technician.getTechExpertiseAreas(),
                        technician.getTechStatus()
                    )
                )

                Log.d("TAG", "Hello Main 2")

                call.enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody> ) {

                        Log.d("TAG", "Hello Main 3")

                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                Log.d("TAG", "Hello Main 4")
                                val jsonString = it.string();
                                val jsonObject = JSONObject(jsonString)
                                status = jsonObject.optString("status").toInt()
                                message = jsonObject.optString("message")
                                data = jsonObject.optString("data")

                                Log.d("status", "$status")
                                Log.d("message", "$message")
                                Log.d("data", "$data")

                                if (status == 201) {
                                    _backendState.value = _backendState.value.copy(
                                        loading = false,
                                        error = null,
                                        response = ResponseObject(status, message, data)
                                    )
                                } else if (status == 500) {
                                    // handle this content
                                    _backendState.value = _backendState.value.copy(
                                        loading = false,
                                        error = ResponseObject(status, message, data),
                                        response = null
                                    )
                                } else {
                                    _backendState.value = _backendState.value.copy(
                                        loading = false,
                                        error = ResponseObject(status, message, data),
                                        response = null
                                    )
                                }
                            }
                        }
                        Log.d("backendState check", "${backendState.value.response?.message}")
                        Log.d("backendState check", "${backendState.value.error?.data}")
                        Log.d("backendState check", "${backendState.value.loading}")
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        _backendState.value = _backendState.value.copy(
                            loading = false,
                            error = ResponseObject(status, message, data),
                            response = null
                        )
                    }

                })

            } catch (e: Exception) {
                _backendState.value = _backendState.value.copy(
                    loading = false,
                    error = ResponseObject(status, message, data),
                    response = null
                )
            }
        }
//        return responseState
    }
}