package com.example.garage.repository

import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.compose.material3.rememberDrawerState
import com.google.gson.internal.bind.TypeAdapters
import com.google.gson.internal.bind.TypeAdapters.URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

object GarageApiClient {
    private const val BASE_URL = "http://192.168.1.21:8082/RoadRescueBackend/garage"

    fun getGarage(){
        val response= URL(BASE_URL).readText()
    }

    fun createGarage(garage:String){
        val x:String=garage
        val connection=URL(BASE_URL).openConnection()as HttpURLConnection
        connection.requestMethod="POST"
        connection.doOutput=true
        connection.outputStream.use { x}

        val response=connection.inputStream.bufferedReader().readText()
    }
}