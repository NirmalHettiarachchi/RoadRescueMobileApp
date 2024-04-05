package eu.tutorials.roadrescuecustomer.models

data class RequestModel(
    var id: String,
    var customer_id: String,
    var issue_category_id: String,
    var indicator_1: Boolean,
    var indicator_2: Boolean,
    var indicator_3: Boolean,
    var indicator_4: Boolean,
    var indicator_5: Boolean,
    var indicator_6: Boolean,
    var vehicle_type_id: String,
    var vehicle_make_id: String,
    var vehicle_model_id: String,
    var fuel_type_id:String,
    var description: String,
    var status: String,
    var location: String,
    var paid_amount: String,
    var rating: String
)