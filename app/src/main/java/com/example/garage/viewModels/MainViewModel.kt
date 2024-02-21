package com.example.garage.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garage.models.GarageTechnician
import com.example.garage.models.NewTechnician
import com.example.garage.models.ResponseState
import com.example.garage.repository.garageService
import kotlinx.coroutines.launch
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    private val _backendState = mutableStateOf(ResponseState())
    val backendState: State<ResponseState> = _backendState

    var status:String?=null
    var message:String?=null
    var data:Any?=null

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
                                val status = jsonObject.optString("status")
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                Log.d("TAG", status)
                                Log.d("TAG", message)
                                Log.d("TAG", data)

                                _backendState.value = _backendState.value.copy(
                                    loading = false,
                                    error = null,
                                    response = data
                                )


                            }

                            Log.d("Successfully", response.body().toString())

                        } else {
                            Log.d("Unsuccessfully", "response is not successfully")
                            _backendState.value = _backendState.value.copy(
                                loading = false,
                                error = response.toString(),
                                response = null

                            )
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("onFailure", "response onFailure")
                        _backendState.value = _backendState.value.copy(
                            loading = false,
                            error = "Error (on failure function ) : ${t.message}"

                        )
                    }

                })

            } catch (e: Exception) {
                _backendState.value = _backendState.value.copy(
                    loading = false,
                    error = "Error (catch function ): ${e.message}"

                )
            }
        }
    }


    fun addTechnician(technician: GarageTechnician) {

        /*val list= buildJsonArray {
            technician.getTechExpertiseAreas()?.forEach {temp->
                add(temp)
            }
        }

        val techObject= buildJsonObject {
            put("techFirstName",technician.getTechFirstName())
            put("techLastName",technician.getTechFirstName())
            put("techContactNumber",technician.getTechContactNumber())
            put("techStatus",technician.getTechStatus())
            put("techExpertiseAreas",list)
        }*/

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
                call.enqueue(object :Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>,
                    ) {

                        Log.d("TAG", "Hello Main 3")

                        val responseBody=response.body()
                        responseBody?.let {
                            Log.d("TAG", "Hello Main 4")
                            var jsonString=it.string();
                            var jsonObject=JSONObject(jsonString)
                             status=jsonObject.optString("status")
                             message=jsonObject.optString("message")
                             data=jsonObject.optString("data")
                        }

                        if (response.code()==200){
                            _backendState.value.copy(
                                loading = false,
                                error = null,
                                response=message
                            )
                        }else if (response.code()==400){
                            // handle this content
                            _backendState.value.copy(
                                loading = false,
                                error = data,
                                response=null
                            )
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }catch (e:Exception){
                _backendState.value = _backendState.value.copy(
                    loading = false,
                    error = "Error (catch function ): ${e.message}"
                )
            }
        }

    }

}