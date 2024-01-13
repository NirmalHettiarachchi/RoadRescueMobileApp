package eu.tutorials.roadrescuecustomer.api

import eu.tutorials.roadrescuecustomer.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("Api.php?apicall=signup")
    fun signup(
        @Field("username") param1: String,
        @Field("password") param2: String,
        @Field("name") param3: String,
        @Field("email") param4: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("Api.php?apicall=login")
    fun login(
        @Field("username") param1: String,
        @Field("password") param2: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("Api.php?apicall=update_profile")
    fun update_profile(
        @Field("username") param1: String,
        @Field("name") param2: String,
        @Field("email") param3: String
    ): Call<LoginResponse>

}
