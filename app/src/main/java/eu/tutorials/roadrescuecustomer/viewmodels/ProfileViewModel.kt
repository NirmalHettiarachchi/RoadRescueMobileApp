package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.AppConfig
import eu.tutorials.roadrescuecustomer.models.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.DriverManager

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

    fun changePhoneNumber(newPhoneNumber: String, customerId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                changePhoneNumberFromDatabase(newPhoneNumber, customerId)
            }
        }
    }

    fun changePhoneNumberFromDatabase(newPhoneNumber: String, customerId: String) {
        try {
            val url = AppConfig.DATABASE_URL
            val username = AppConfig.DATABASE_USERNAME
            val databasePassword = AppConfig.DATABASE_PASSWORD

            Class.forName("com.mysql.jdbc.Driver")
            DriverManager.getConnection(url, username, databasePassword).use { connection ->

                val statusQuery = "UPDATE customer SET phone_number = ? WHERE id = ?"

                connection.prepareStatement(statusQuery).use { preparedStatement ->

                    preparedStatement.setString(1, newPhoneNumber)
                    preparedStatement.setString(2, customerId)

                    preparedStatement.executeUpdate()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

