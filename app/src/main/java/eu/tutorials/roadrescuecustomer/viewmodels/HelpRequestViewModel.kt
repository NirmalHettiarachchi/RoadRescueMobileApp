package eu.tutorials.roadrescuecustomer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.HelpRequest
import eu.tutorials.roadrescuecustomer.models.HelpRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import android.util.Log

class HelpRequestViewModel : ViewModel() {
    private val _helpRequests = MutableStateFlow<List<HelpRequest>>(emptyList())
    val helpRequests: StateFlow<List<HelpRequest>> = _helpRequests

    init {
        fetchHelpRequests()
    }

    private fun fetchHelpRequests() {
        viewModelScope.launch {
            HelpRequestRepository.fetchHelpRequests(
                onSuccess = { helpRequests ->
                    // Update the UI with the fetched help requests
                    _helpRequests.value = helpRequests
                },
                onError = { exception ->
                    // Log the error and optionally update the UI to reflect the error state
                    Log.e("HelpRequestViewModel", "Error fetching help requests", exception)

                    // Example: Update _helpRequests to reflect an error state if your UI can handle it.
                    // This could involve setting _helpRequests to an empty list, a specific error state,
                    // or triggering a UI component to display an error message.
                    // _helpRequests.value = listOf()
                }
            )
        }
    }
}