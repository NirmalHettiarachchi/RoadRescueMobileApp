package eu.tutorials.roadrescuecustomer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.tutorials.roadrescuecustomer.R

class NavigationViewModel : ViewModel() {
    private val _selectedFooterIcon = MutableLiveData<Int?>(R.drawable.home)
    val selectedFooterIcon: LiveData<Int?> = _selectedFooterIcon

    fun selectFooterIcon(iconId: Int?) {
        _selectedFooterIcon.value = iconId
    }
}