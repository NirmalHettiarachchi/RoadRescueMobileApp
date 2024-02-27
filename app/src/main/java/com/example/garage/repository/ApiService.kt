package com.example.garage.repository

import com.example.garage.models.NewTechnician
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


private val ipV4Address="192.168.106.117"
private val retrofit = Retrofit.Builder().baseUrl("http://${ipV4Address}:8082/roadRescueBackend/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val garageService = retrofit.create(ApiService::class.java)

interface ApiService {

    @GET("garage")
    fun getData(): Call<ResponseBody>

    @GET("technician")
    fun getTechnician(): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("technician")
    fun postTechnician(@Body technician: NewTechnician): Call<ResponseBody>


}