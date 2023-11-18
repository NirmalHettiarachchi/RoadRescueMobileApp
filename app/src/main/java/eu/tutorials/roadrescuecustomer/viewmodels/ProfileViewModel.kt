package eu.tutorials.roadrescuecustomer.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tutorials.roadrescuecustomer.models.ProfileRepository

class ProfileViewModel : ViewModel() {
    private val _repository: ProfileRepository = ProfileRepository()

    private val _name = mutableStateOf(_repository.getProfile().name)
    private val _email = mutableStateOf(_repository.getProfile().email)
    private val _phoneNumber = mutableStateOf(_repository.getProfile().phoneNumber)
    private val _numOfServiceRequests = mutableIntStateOf(_repository.getProfile().numOfServiceRequests)

    val name: MutableState<String> = _name
    val email: MutableState<String?> = _email
    val phoneNumber: MutableState<String> = _phoneNumber
    val numOfServiceRequests: MutableState<Int> = _numOfServiceRequests

    fun setProfile(name: String, email: String?, phoneNumber: String, numOfServiceRequests: Int) {
        _repository.setProfile(name, email, phoneNumber, numOfServiceRequests)
        _name.value = _repository.getProfile().name
        _email.value = _repository.getProfile().email
        _phoneNumber.value = _repository.getProfile().phoneNumber
        _numOfServiceRequests.intValue = _repository.getProfile().numOfServiceRequests
    }
}

