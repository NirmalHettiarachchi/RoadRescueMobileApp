package eu.tutorials.roadrescuecustomer.data.model

data class PaymentResponse(
    val status : Int,
    val paymentIntent : String,
    val ephemeralKey : String,
    val customer : String,
    val publishableKey : String
)
