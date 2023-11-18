package eu.tutorials.roadrescuecustomer.models

data class ServiceRequest(
    var issue: String,
    var vehicleType: String,
    var fuelType: String,
    var approximatedCost: Double,
    var description: String,
    var status: String = "Pending"
)

class ServiceRequestRepository {

    private var _serviceRequest = ServiceRequest("", "", "", 0.00, "")

    fun getServiceRequest() = _serviceRequest

    fun setServiceRequest(issue: String, vehicleType: String, fuelType: String, approximatedCost: Double, description: String) {
        _serviceRequest.issue = issue
        _serviceRequest.vehicleType = vehicleType
        _serviceRequest.fuelType = fuelType
        _serviceRequest.approximatedCost = approximatedCost
        _serviceRequest.description = description
    }
}
