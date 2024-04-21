package eu.tutorials.roadrescuecustomer.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tutorials.roadrescuecustomer.models.LocationData

class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

    fun resetLocation() {
        _location.value = null
    }
}