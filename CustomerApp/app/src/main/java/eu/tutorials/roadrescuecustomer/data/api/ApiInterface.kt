package eu.tutorials.roadrescuecustomer.data.api

import eu.tutorials.roadrescuecustomer.data.model.PaymentResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("stripe-android-api/")
    suspend fun getPaymentDetail(
        @Field("amount") amount : Int,
        @Field("description") description : String,
        @Field("name") name : String,
    ) : Response<PaymentResponse>
}