package eu.tutorials.roadrescuecustomer.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tutorials.roadrescuecustomer.models.CurrentStateRepository

class CurrentStateViewModel : ViewModel() {
    private val _repository: CurrentStateRepository = CurrentStateRepository()
    private val _isServiceRequested = mutableStateOf(_repository.getCurrentState().isServiceRequested)
    private val _isReqServiceWindowOpened = mutableStateOf(_repository.getCurrentState().isReqServiceWindowOpened)

    val isServiceRequested: MutableState<Boolean> = _isServiceRequested
    val isReqServiceWindowOpened: MutableState<Boolean> = _isReqServiceWindowOpened

    fun setCurrentState(isServiceRequested: Boolean, isReqServiceWindowOpened: Boolean) {
        _repository.setCurrentState(isServiceRequested, isReqServiceWindowOpened)
        _isServiceRequested.value = _repository.getCurrentState().isServiceRequested
        _isReqServiceWindowOpened.value = _repository.getCurrentState().isReqServiceWindowOpened
    }
}