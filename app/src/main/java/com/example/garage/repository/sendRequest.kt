
package com.example.garage.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

suspend fun sendRequest(url:String):String{


    return withContext(Dispatchers.IO){

        val connection=URL(url).openConnection()as HttpURLConnection
        connection.requestMethod="Get"

        val responceCode=connection.responseCode
        if (responceCode==HttpURLConnection.HTTP_OK){
            val inputStream=connection.inputStream
            val responce=inputStream.bufferedReader().use { it.readText() }
            inputStream.close()
            Log.d("response","response is ok ")
            connection.disconnect()
            responce
        }else{
            connection.disconnect()
            throw Exception("Failed Response Code $responceCode")
        }
    }

}