package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _repository: ProfileRepository = ProfileRepository()
    private val _numOfServiceRequests = mutableIntStateOf(_repository.getProfile().numOfServiceRequests)
    val numOfServiceRequests: MutableState<Int> = _numOfServiceRequests

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun updateProfile(phoneNumber: String, name: String, email: String, context: Context) {
        viewModelScope.launch {
            _loading.value = true
            _repository.updateProfile(phoneNumber, name, email, context)
            delay(2400)
            _loading.value = false
        }
    }
}

