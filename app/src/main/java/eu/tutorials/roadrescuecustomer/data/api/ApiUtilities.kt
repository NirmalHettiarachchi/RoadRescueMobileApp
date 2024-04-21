package eu.tutorials.roadrescuecustomer.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {


    fun getApiInterface() : ApiInterface {
        return Retrofit.Builder()
            .baseUrl("http://nadeen.alwaysdata.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

}