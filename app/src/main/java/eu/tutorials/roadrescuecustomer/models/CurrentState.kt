package eu.tutorials.roadrescuecustomer.models

data class CurrentStateModel(var isServiceRequested: Boolean)

class CurrentStateRepository {
    private var _currentState = CurrentStateModel(false)

    fun getCurrentState() = _currentState

    fun setCurrentState(isServiceRequested: Boolean) {
        _currentState.isServiceRequested = isServiceRequested
    }
}

