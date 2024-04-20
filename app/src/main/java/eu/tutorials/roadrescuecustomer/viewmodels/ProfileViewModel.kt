package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.ProfileRepository
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import kotlinx.coroutines.CoroutineScope
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
            val DATABASE_NAME = "road_rescue"
            val url =
                "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
            val username = "admin"
            val databasePassword = "admin123"

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

