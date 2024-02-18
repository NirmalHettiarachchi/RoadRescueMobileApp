package com.example.garage.models

data class ResponseState(
    val loading:Boolean=true,
    val error:String?=null,
    val response:String?=null
)
