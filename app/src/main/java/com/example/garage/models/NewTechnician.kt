package com.example.garage.models

import com.google.gson.annotations.SerializedName

data class NewTechnician(
    @SerializedName("techFirstName")
    val techFirstName:String,
    @SerializedName("techLastName")
    val techLastName:String,
    @SerializedName("techContactNumber")
    val techContactNumber:String,
    @SerializedName("techExpertiseAreas")
    val techExpertiseAreas:List<String>,
    @SerializedName("techStatus")
    val techStatus:Int,
)

data class SearchVarible(
    @SerializedName("searchId")
    val searchId:String
)