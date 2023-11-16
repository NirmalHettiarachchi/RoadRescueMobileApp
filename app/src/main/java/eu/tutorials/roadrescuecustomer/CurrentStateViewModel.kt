package eu.tutorials.roadrescuecustomer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CurrentStateViewModel : ViewModel() {
    private val _repository: CurrentStateRepository = CurrentStateRepository()
    private val _isServiceRequested = mutableStateOf(_repository.getCurrentState().isServiceRequested)

    val isServiceRequested: MutableState<Boolean> = _isServiceRequested

    fun setCurrentState(isServiceRequested: Boolean) {
        _repository.setCurrentState(isServiceRequested)
        _isServiceRequested.value = _repository.getCurrentState().isServiceRequested
    }
}