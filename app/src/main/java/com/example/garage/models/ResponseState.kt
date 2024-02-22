package com.example.garage.models

data class ResponseState(
    val loading:Boolean=true,
    val error: ResponseObject?=null,
    val response:ResponseObject?=null
)

data class ResponseObject(
    val status:Int,
    val message:String,
    val data:Any?=null
)
