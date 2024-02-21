package com.example.garage.models

data class ResponseState(
    val loading:Boolean=true,
    val error:Any?=null,
    val response:Any?=null
)
