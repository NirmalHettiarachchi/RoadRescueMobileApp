package com.example.garage.repository

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            Log.d("TAG", "HEllo Main")
            try {
                val call= garageService.getData()
                call.enqueue(object :Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>,
                    ) {
                        if (response.isSuccessful){
                            val  responseBody=response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status")
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                Log.d("TAG", status)
                                Log.d("TAG", message)
                                Log.d("TAG", data)

                                _backendState.value=_backendState.value.copy(
                                    loading = false,
                                    error = null,
                                    response = data
                                )


                         }

                            Log.d("Successfully",response.body().toString())

                        }else{
                            Log.d("Unsuccessfully","response is not successfully")
                            _backendState.value=_backendState.value.copy(
                                loading = false,
                                error = response.toString(),
                                response = null

                            )
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("onFailure","response onFailure")
                        _backendState.value=_backendState.value.copy(
                            loading = false,
                            error = "Error (on failure function ) : ${t.message}"

                        )
                    }

                })

            }catch (e:Exception){
                _backendState.value=_backendState.value.copy(
                    loading = false,
                    error = "Error (catch function ): ${e.message}"

                )
            }
        }
    }

}