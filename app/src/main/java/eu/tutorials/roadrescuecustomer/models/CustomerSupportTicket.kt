package eu.tutorials.roadrescuecustomer.models

data class CustomerSupportTicket(
    val id: Int,
    val customerId: Int,
    val issue: String,
    val date: String,
    val status: String,
    val description: String,
    val solution: String
)