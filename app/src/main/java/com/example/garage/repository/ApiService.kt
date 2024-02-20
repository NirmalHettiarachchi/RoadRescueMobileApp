package com.example.garage.repository


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PUT


private val retrofit=Retrofit.Builder().baseUrl("http://10.22.162.54:8082/RoadRescueBackend/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val garageService= retrofit.create(ApiService::class.java)

interface ApiService {

    @GET("garage")
   fun getData():Call<ResponseBody>
   @GET("technician")
   fun getTechnician():Call<ResponseBody>
   @PUT("technician")
   fun putTechnician():Call<ResponseBody>


}