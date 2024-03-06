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
import retrofit2.http.Query


// redmi ip = 192.168.38.117
// student wifi = 10.22.162.54
// bijja = 192.168.1.102


private val ipV4Address="10.22.162.54"
private val retrofit = Retrofit.Builder().baseUrl("http://${ipV4Address}:8082/roadRescueBackend/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val garageService = retrofit.create(ApiService::class.java)

interface ApiService {

    @GET("garage")
    fun getData(): Call<ResponseBody>
    @Headers("Content-Type: application/json")
    @GET("technician")
    fun getTechnician(@Query("searchId") searchId: String,@Query("option") option:String): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("technician")
    fun postTechnician(@Body technician: NewTechnician): Call<ResponseBody>


}