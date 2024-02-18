package com.example.garage.repository

import android.util.JsonReader
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private val retrofit=Retrofit.Builder().baseUrl("http://192.168.43.117:8082/RoadRescueBackend/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val garageService= retrofit.create(ApiService::class.java)

interface ApiService {

    @GET("garage")
   suspend fun getData():String
}