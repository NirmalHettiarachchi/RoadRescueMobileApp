package com.example.garage.repository

import com.example.garage.models.NewTechnician
import com.example.garage.models.UpdateGarage
import com.example.garage.models.UpdateTechnician
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query




private val ipV4Address="192.168.28.117"
private val retrofit = Retrofit.Builder().baseUrl("http://${ipV4Address}:8082/roadRescueBackend/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val garageService = retrofit.create(ApiService::class.java)

interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("garage")
    fun getGarageData(@Query("searchId") searchId: String,@Query("option") option:String): Call<ResponseBody>


    @Headers("Content-Type: application/json")
    @PUT("garage")
    fun updateGarage(@Body garage: UpdateGarage):Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("technician")
    fun getTechnician(@Query("searchId") searchId: String,@Query("option") option:String): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("technician")
    fun postTechnician(@Body technician: NewTechnician): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @DELETE("technician")
    fun deleteTechnician(@Query("delId") delId: String):Call<ResponseBody>
    @Headers("Content-Type: application/json")
    @PUT("technician")
    fun updateTechnician(@Body technician: UpdateTechnician):Call<ResponseBody>


    @Headers("Content-Type: application/json")
    @GET("service")
    fun getServiceRequests(@Query("searchId") searchId: String,@Query("option") option:String): Call<ResponseBody>
    @PUT("service")
    fun updateServiceRequest(
        @Query("option") option:String,
        @Query("serviceId")serviceId:Int,
        @Query("serviceProviderId")serviceProviderId:String,
        @Query("technicianId")technicianId:String
        ): Call<ResponseBody>
}