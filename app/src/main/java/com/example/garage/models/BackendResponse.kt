package com.example.garage.models

import com.google.gson.annotations.SerializedName

data class BackendResponse(
    @SerializedName("status")
    val status:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("data")
    val data:String
)

data class MyResponse(
    val lisiData:List<BackendResponse>
)
