package com.example.garage.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garage.models.Garage
import com.example.garage.models.GarageTechnician
import com.example.garage.models.IssueSupportTicket
import com.example.garage.models.NewSupportTicket
import com.example.garage.models.NewTechnician
import com.example.garage.models.ResponseObject
import com.example.garage.models.UpdateGarage
import com.example.garage.repository.garageService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


//    val backendState = MutableLiveData(ResponseState())

    //Login page

    suspend fun checkPhoneNumberIsExists(
        phoneNumber:String,
        option:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ){
        val deferred = CompletableDeferred<ResponseObject>()
        try {
            val call = garageService.getGarageData(phoneNumber,option)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            val jsonString = it.string() // Convert response body to JSON string
                            val jsonObject = JSONObject(jsonString)
                            val status = jsonObject.optString("status").toInt()
                            val message = jsonObject.optString("message")
                            val data = jsonObject.optString("data")

                            val responseObject = ResponseObject(status, message, data)

                            onResponseReceived(responseObject)
                            deferred.complete(responseObject)
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    deferred.completeExceptionally(t)
                }

            })

        } catch (e: Exception) {
            deferred.completeExceptionally(e)
        }catch (e:JSONException){
            deferred.completeExceptionally(e)
        }
        deferred.await()
    }




    suspend fun getExpertiseArias(
        searchId:String,
        option:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ){
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.getTechnician(searchId,option)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }

    suspend fun getTechnicians(
        searchId:String,
        option:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ) {

        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.getTechnician(searchId,option)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }



                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }


    suspend fun addTechnician(
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


    suspend fun delTechnician(
        delId:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ){
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
               val call = garageService.deleteTechnician(delId)
                call.enqueue(object :Callback<ResponseBody>{

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })
            }catch (e:Exception){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }

    suspend fun updateTechnician(
        technician: GarageTechnician,
        onResponseReceived: (ResponseObject?) -> Unit // Lambda parameter to execute after receiving the response
    ){
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try{
                val call = garageService.updateTechnician(
                    com.example.garage.models.UpdateTechnician(
                        technician.getTechId(),
                        technician.getTechFirstName(),
                        technician.getTechLastName(),
                        technician.getTechImageRef(),
                        technician.getTechExpertiseAreas()
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
            }catch (e:Exception) {
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }






    //---------------------------------------------------------Garage Section---------------------------------------------------------------

    suspend fun getGarageDetails(
        searchId:String,
        option:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ) {

        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.getGarageData(searchId,option)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }catch (e:JSONException){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }


    suspend fun updateGarage(
        garage:Garage,
        onResponseReceived: (ResponseObject?) -> Unit
    ){
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch{
            try {

                val call = garageService.updateGarage(UpdateGarage(
                    garage.getGarageId(),
                    garage.getGarageName(),
                    garage.getOwnerName(),
                    garage.getGarageContactNumber(),
                    garage.getGarageEmail(),
                    garage.getGarageProfilePicRef()
                ))

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
            }catch (e:Exception){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }

    suspend fun getGarageServiceRequest(
        searchId:String,
        option:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ){
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.getServiceRequests(searchId,option)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }catch (e:JSONException){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }


    suspend fun assignTechnicianForService(
        option: String,
        serviceRequestId:Int,
        serviceProviderId:String,
        technicianId:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ){
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.updateServiceRequest(option = option,serviceId = serviceRequestId, serviceProviderId =serviceProviderId, technicianId = technicianId)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }catch (e:JSONException){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }


    suspend fun getActivities(
        searchId:String,
        option: String,
        onResponseReceived: (ResponseObject?) -> Unit
    ){

        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.getGarageData(searchId,option)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }catch (e:JSONException){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }

    suspend fun getCustomerSupport(
        searchId:String,
        option:String,
        onResponseReceived: (ResponseObject?) -> Unit)
    {
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.getGarageData(searchId,option)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }catch (e:JSONException){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }

    suspend fun sendSupportTicket(
        supportTicket: IssueSupportTicket,
        option:String,
        onResponseReceived: (ResponseObject?) -> Unit
    ){
        val deferred = CompletableDeferred<ResponseObject>()

        viewModelScope.launch {
            try {
                val call = garageService.postSupportTicket(
                    NewSupportTicket(
                        supportTicket.getSpId(),
                        supportTicket.getTitle(),
                        supportTicket.getDescription(),option
                    )
                )
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                val jsonString = it.string() // Convert response body to JSON string
                                val jsonObject = JSONObject(jsonString)
                                val status = jsonObject.optString("status").toInt()
                                val message = jsonObject.optString("message")
                                val data = jsonObject.optString("data")

                                val responseObject = ResponseObject(status, message, data)

                                onResponseReceived(responseObject)
                                deferred.complete(responseObject)
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        deferred.completeExceptionally(t)
                    }

                })

            } catch (e: Exception) {
                deferred.completeExceptionally(e)
            }catch (e:JSONException){
                deferred.completeExceptionally(e)
            }
        }
        deferred.await()
    }


}