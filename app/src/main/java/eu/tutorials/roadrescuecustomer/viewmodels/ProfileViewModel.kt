package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tutorials.roadrescuecustomer.models.ProfileRepository

class ProfileViewModel : ViewModel() {
    private val _repository: ProfileRepository = ProfileRepository()
    private val _numOfServiceRequests =
        mutableIntStateOf(_repository.getProfile().numOfServiceRequests)
    val numOfServiceRequests: MutableState<Int> = _numOfServiceRequests

    fun updateProfile(phoneNumber: String, name: String, email: String, context: Context) {
        _repository.updateProfile(phoneNumber, name, email, context)
    }
}

