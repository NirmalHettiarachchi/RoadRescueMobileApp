package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.CurrentStateRepository
import eu.tutorials.roadrescuecustomer.models.Issues
import eu.tutorials.roadrescuecustomer.models.ServiceRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CurrentStateViewModel : ViewModel() {
    private val _repository: CurrentStateRepository = CurrentStateRepository()
    private val _isServiceRequested = mutableStateOf(_repository.getCurrentState().isServiceRequested)
    private val _isReqServiceWindowOpened = mutableStateOf(_repository.getCurrentState().isReqServiceWindowOpened)

    val isServiceRequested: MutableState<Boolean> = _isServiceRequested
    val isReqServiceWindowOpened: MutableState<Boolean> = _isReqServiceWindowOpened

    private val _latestRequests = MutableStateFlow(emptyList<ServiceRequest>())
    val latestRequests = _latestRequests.asStateFlow()

    val loading = mutableStateOf(false)

    fun setCurrentState(isServiceRequested: Boolean, isReqServiceWindowOpened: Boolean) {
        _repository.setCurrentState(isServiceRequested, isReqServiceWindowOpened)
        _isServiceRequested.value = _repository.getCurrentState().isServiceRequested
        _isReqServiceWindowOpened.value = _repository.getCurrentState().isReqServiceWindowOpened
    }

    fun fetchLatestRequest(customerId: String) {
        viewModelScope.launch {
            loading.value = true
            val fetchedLatestRequests = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                fetchLatestRequestFromDatabase(customerId)
            }
            Log.d(
                "TAG",
                "fetchLatestRequest: fetchedLatestRequests : ${fetchedLatestRequests.toString()}"
            )
            _latestRequests.value = fetchedLatestRequests
            Log.d("TAG", "fetchLatestRequest: Customer ID : $customerId")
            Log.d("TAG", "fetchLatestRequest: Latest Records :: ${latestRequests.value.size}")
            loading.value = false
        }
    }

    fun clearRecentRequest(){
        _latestRequests.value = emptyList()
    }

    private fun fetchLatestRequestFromDatabase(customerId: String): List<ServiceRequest> {
        val latestRequests = mutableListOf<ServiceRequest>()
        try {
            val DATABASE_NAME = "road_rescue"
            val url =
                "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/" + DATABASE_NAME
            val username = "admin"
            val databasePassword = "admin123"

            Class.forName("com.mysql.jdbc.Driver")
            val connection: Connection =
                DriverManager.getConnection(url, username, databasePassword)
            val statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT *, CONVERT_TZ(request_timestamp, '+00:00', '+05:30') AS request_timestamp_ist FROM service_request WHERE customer_id = $customerId AND ((status = 1 AND CONVERT_TZ(request_timestamp, '+00:00', '+05:30') > NOW() - INTERVAL 3 MINUTE) OR status = 2) ORDER BY request_timestamp DESC LIMIT 1;")

            while (resultSet.next()) {
                Log.d(
                    "TAG",
                    "fetchLatestRequestFromDatabase: Data Fetched : ${resultSet.getString("customer_id")}"
                )
                val id = resultSet.getString("id")
                val customerId = resultSet.getString("customer_id")
                val issueCategoryId = resultSet.getString("issue_category_id")
                val vehicleTypeId = resultSet.getString("vehicle_type_id")
                val vehicleMakeId = resultSet.getString("vehicle_make_id")
                val vehicleModelId = resultSet.getString("vehicle_model_id")
                val fuelTypeId = resultSet.getString("fuel_type_id")
                val description = resultSet.getString("description")
                val status = resultSet.getString("status")
                val location = resultSet.getString("location")
                val paidAmount = resultSet.getString("paid_amount")
                val rating = resultSet.getString("rating")
                val date = resultSet.getString("request_timestamp")
                val update = resultSet.getTimestamp("updated_at")

                val serviceRequest = ServiceRequest(
                    id = id,
                    customerId = customerId,
                    issueCategoryId = issueCategoryId,
                    vehicleTypeId = vehicleTypeId,
                    vehicleMakeId = vehicleMakeId,
                    vehicleModelId = vehicleModelId,
                    fuelTypeId = fuelTypeId,
                    description = description,
                    status = status,
                    location = location,
                    paidAmount = paidAmount,
                    rating = rating,
                    date =  date.toString()
                    )
                latestRequests.add(serviceRequest)
            }
            connection.close()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("TAG", "fetchLatestRequestFromDatabase: Return Result ${latestRequests.size}")
        return latestRequests
    }
}