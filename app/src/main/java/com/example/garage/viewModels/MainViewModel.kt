package com.example.garage.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garage.models.GarageTechnician
import com.example.garage.models.NewTechnician
import com.example.garage.models.ResponseObject
import com.example.garage.models.ResponseState
import com.example.garage.repository.garageService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    val backendState = MutableLiveData(ResponseState())

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

                                backendState.value = backendState.value?.copy(
                                    loading = false,
                                    error = null,
                                    response = ResponseObject(status, message, data)
                                )


                            }

                            Log.d("Successfully", response.body().toString())

                        } else {
                            Log.d("Unsuccessfully", "response is not successfully")
                            backendState.value = backendState.value?.copy(
                                loading = false,
                                error = ResponseObject(400, "Bad Request", "Bad Request"),
                                response = null

                            )
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("onFailure", "response onFailure")
                        backendState.value = backendState.value?.copy(
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
                backendState.value = backendState.value?.copy(
                    loading = false,
                    error = ResponseObject(405, "Method Not Allowed", "Method Not Allowed")

                )
            }
        }
    }


    suspend fun addTechnicianTest(
        technician: GarageTechnician,
        onResponseReceived: (ResponseObject?) -> Unit // Lambda parameter to execute after receiving the response
    ) {
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.postTechnician(
                    NewTechnician(
                        technician.getTechFirstName(),
                        technician.getTechLastName(),
                        technician.getTechContactNumber(),
                        technician.getTechExpertiseAreas(),
                        technician.getTechStatus()
                    )
                )

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string()
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)


                                onResponseReceived(responseObject) // Execute the function passed as lambda parameter
                                deferred.complete(responseObject)
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // Handle failure
                        deferred.completeExceptionally(t)
                    }
                })
            } catch (e: Exception) {
                // Handle exception
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }
}