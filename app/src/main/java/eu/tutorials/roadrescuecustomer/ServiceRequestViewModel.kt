package eu.tutorials.roadrescuecustomer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ServiceRequestViewModel : ViewModel() {
    private val _repository: ServiceRequestRepository = ServiceRequestRepository()

    private val _issue = mutableStateOf(_repository.getServiceRequest().issue)
    private val _vehicleType = mutableStateOf(_repository.getServiceRequest().vehicleType)
    private val _fuelType = mutableStateOf(_repository.getServiceRequest().fuelType)
    private val _approximatedCost = mutableDoubleStateOf(_repository.getServiceRequest().approximatedCost)
    private val _description = mutableStateOf(_repository.getServiceRequest().description)

    val issue: MutableState<String> = _issue
    val vehicleType: MutableState<String> = _vehicleType
    val fuelType: MutableState<String> = _fuelType
    val approximatedCost: MutableState<Double> = _approximatedCost
    val description: MutableState<String> = _description

    fun setServiceRequest(issue: String, vehicleType: String, fuelType: String, approximatedCost: Double, description: String) {
        _repository.setServiceRequest(issue, vehicleType, fuelType, approximatedCost, description)
        _issue.value = _repository.getServiceRequest().issue
        _vehicleType.value = _repository.getServiceRequest().vehicleType
        _fuelType.value = _repository.getServiceRequest().fuelType
        _approximatedCost.doubleValue = _repository.getServiceRequest().approximatedCost
        _description.value = _repository.getServiceRequest().description
    }
}

