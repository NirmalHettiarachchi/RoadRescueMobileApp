package eu.tutorials.roadrescuecustomer.models

data class CurrentStateModel(var isServiceRequested: Boolean, var isReqServiceWindowOpened: Boolean)

class CurrentStateRepository {
    private var _currentState = CurrentStateModel(false, isReqServiceWindowOpened = false)

    fun getCurrentState() = _currentState

    fun setCurrentState(isServiceRequested: Boolean, isReqServiceWindowOpened: Boolean) {
        _currentState.isServiceRequested = isServiceRequested
        _currentState.isReqServiceWindowOpened = isReqServiceWindowOpened
    }
}

