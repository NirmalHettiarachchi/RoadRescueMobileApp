package eu.tutorials.roadrescuecustomer.models

data class ServiceRequest(
    var issue: String,
    var vehicleType: String,
    var fuelType: String,
    var vehicleMake: String,
    var vehicleModel: String,
    var approximatedCost: Double,
    var description: String,
    var status: String = "Pending"
)

class ServiceRequestRepository {

    private var _serviceRequest = ServiceRequest("", "", "", "", "",0.00, "")

    fun getServiceRequest() = _serviceRequest

    fun setServiceRequest(issue: String, vehicleType: String, fuelType: String, vehicleMake: String, vehicleModel: String, approximatedCost: Double, description: String) {
        _serviceRequest.issue = issue
        _serviceRequest.vehicleType = vehicleType
        _serviceRequest.fuelType = fuelType
        _serviceRequest.vehicleMake = vehicleMake
        _serviceRequest.vehicleModel = vehicleModel
        _serviceRequest.approximatedCost = approximatedCost
        _serviceRequest.description = description
    }
}
