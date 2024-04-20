package eu.tutorials.roadrescuecustomer.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.PaymentSheetResultCallback
import eu.tutorials.roadrescuecustomer.data.api.ApiInterface
import eu.tutorials.roadrescuecustomer.data.api.ApiUtilities
import eu.tutorials.roadrescuecustomer.data.model.PaymentResponse
import eu.tutorials.roadrescuecustomer.models.CustomerSupportTicket
import eu.tutorials.roadrescuecustomer.models.FuelType
import eu.tutorials.roadrescuecustomer.models.Issues
import eu.tutorials.roadrescuecustomer.models.ServiceRequest
import eu.tutorials.roadrescuecustomer.models.ServiceRequestRepository
import eu.tutorials.roadrescuecustomer.models.VehicleMake
import eu.tutorials.roadrescuecustomer.models.VehicleModel
import eu.tutorials.roadrescuecustomer.models.VehicleType
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.views.getActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
import kotlin.math.log

class ServiceRequestViewModel : ViewModel() {
    private val _repository: ServiceRequestRepository = ServiceRequestRepository()

    private val _issue = mutableStateOf(Issues("", "", ""))
    private val _indicator1 = mutableStateOf(false)
    private val _indicator2 = mutableStateOf(false)
    private val _indicator3 = mutableStateOf(false)
    private val _indicator4 = mutableStateOf(false)
    private val _indicator5 = mutableStateOf(false)
    private val _indicator6 = mutableStateOf(false)
    private val _vehicleType = mutableStateOf(VehicleType("", ""))
    private val _fuelType = mutableStateOf(FuelType("", ""))
    private val _vehicleMake = mutableStateOf(VehicleMake("", ""))
    private val _vehicleModel = mutableStateOf(VehicleModel("", ""))
    private val _description = mutableStateOf(_repository.getServiceRequest().description)
    private val _approxCost = mutableStateOf(_repository.getServiceRequest().approxCost)

    val issue: MutableState<Issues> = _issue
    val indicator1: MutableState<Boolean> = _indicator1
    val indicator2: MutableState<Boolean> = _indicator2
    val indicator3: MutableState<Boolean> = _indicator3
    val indicator4: MutableState<Boolean> = _indicator4
    val indicator5: MutableState<Boolean> = _indicator5
    val indicator6: MutableState<Boolean> = _indicator6
    val vehicleType: MutableState<VehicleType> = _vehicleType
    val fuelType: MutableState<FuelType> = _fuelType
    val vehicleMake: MutableState<VehicleMake> = _vehicleMake
    val vehicleModel: MutableState<VehicleModel> = _vehicleModel
    val description: MutableState<String> = _description
    val approxCost: MutableState<String> = _approxCost

    val requests = mutableStateListOf<ServiceRequest>()

    private val _requestCount = MutableStateFlow(0) // Initial value
    val requestCount: StateFlow<Int> = _requestCount

    private val _payment = MutableSharedFlow<PaymentResponse>()
    val payment = _payment.asSharedFlow()

    val loading = mutableStateOf(false)

    private val _deleteLoading = MutableSharedFlow<Boolean>()
    val deleteLoading = _deleteLoading.asSharedFlow()

    private val _ratingLoading = MutableSharedFlow<Boolean>()
    val ratingLoading = _ratingLoading.asSharedFlow()

    private val _paymentDone = MutableSharedFlow<Boolean>()
    val paymentDone = _paymentDone.asSharedFlow()

    private val _showError = MutableSharedFlow<String>()
    val showError = _showError.asSharedFlow()


    private val _status = MutableSharedFlow<Int>()
    val status = _status.asSharedFlow()

    private var apiInterface = ApiUtilities.getApiInterface()

    fun setServiceRequest(
        context: Context,
        serviceRequest: ServiceRequest, requestCallback: ServiceRequestRepository.RequestCallback
    ) {
        _repository.requestService(
            context,
            serviceRequest,
            requestCallback
        )
    }
    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
//        To handle it here in Checkout ViewModel
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d(TAG, "paymentSheetResult - Canceled")

            }

            is PaymentSheetResult.Failed -> {
                Log.d(TAG, "paymentSheetResult - Error: ${paymentSheetResult.error}")
            }

            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen
                Log.d(TAG, "paymentSheetResult - Completed")
            }
        }
    }
    fun initStipePayment(
        amount: Int,
        description: String,
        name: String
    ) {
        viewModelScope.launch {
            loading.value = true
            val response = apiInterface.getPaymentDetail(
                amount,
                description,
                name
            )
            if (response.isSuccessful) {
                Log.d(TAG, "initStipePayment: paymentIntent ${response.body()?.paymentIntent}")
                Log.d(TAG, "initStipePayment: customer ${response.body()?.customer}")
                Log.d(TAG, "initStipePayment: ephemeralKey ${response.body()?.ephemeralKey}")
                Log.d(TAG, "initStipePayment: publishableKey ${response.body()?.publishableKey}")
                Log.d(TAG, "initStipePayment: ${response.message()}")


                if (response.body() != null) {
                    if(response.body()?.status == 200){
                        _payment.emit(response.body()!!)
                    }else{
                        _showError.emit("Payment Error Retry Please!")
                    }
                    loading.value = false
                }else{
                    _showError.emit("Payment Error Retry Please!")
                    loading.value = false
                }
            } else {
                Log.d(TAG, "initStipePayment: Response Failed")
                loading.value = false
            }
        }
    }

    fun checkRequest(
        context: Context,
        requestCallback: ServiceRequestRepository.RequestCallback
    ) {
        _repository.checkRequest(
            context,
            requestCallback
        )
    }

    fun deleteRequest(context: Context, requestId: Int) {
        viewModelScope.launch {
            Log.d(TAG, "deleteRequest: Started")
            loading.value = true
            Log.d(TAG, "deleteRequest: Delay")
            val deleteResult = withContext(Dispatchers.IO) {
                deleteRequestFromDatabase(context, requestId)
            }

            when (deleteResult) {
                is DeleteResult.Success -> {
                    Toast.makeText(context, "Request cancelled successfully", Toast.LENGTH_SHORT)
                        .show()
                }

                is DeleteResult.Failure -> {
                    // This also runs on the main thread, safe to show a Toast
                    Toast.makeText(context, deleteResult.message, Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
            Log.d(TAG, "deleteRequest: End")
            loading.value = false
            _deleteLoading.emit(true)
        }
    }


    fun rateOrSkip(context: Context, rate: Int = -1, requestId: Int) {
        viewModelScope.launch {
            Log.d(TAG, "rateOrSkip: Started")
            loading.value = true
            Log.d(TAG, "rateOrSkip: Delay")
            val deleteResult = withContext(Dispatchers.IO) {
                rateOrSkipDatabase(context, rate, requestId)
            }

            when (deleteResult) {
                is DeleteResult.Success -> {
                    Toast.makeText(context, "Feedback given successfully", Toast.LENGTH_SHORT)
                        .show()
                }

                is DeleteResult.Failure -> {
                    // This also runs on the main thread, safe to show a Toast
                    Toast.makeText(context, deleteResult.message, Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
            Log.d(TAG, "rateOrSkip: End")
            loading.value = false
            _ratingLoading.emit(true)
        }
    }


    fun paymentDone(context: Context, requestId: Int) {
        viewModelScope.launch {
            Log.d(TAG, "paymentDone: Started")
            loading.value = true
            Log.d(TAG, "paymentDone: Delay")
            val deleteResult = withContext(Dispatchers.IO) {
                paymentDoneDatabase(requestId)
            }

            when (deleteResult) {
                is DeleteResult.Success -> {
                    Toast.makeText(context, "Payment done successfully", Toast.LENGTH_SHORT)
                        .show()
                }

                is DeleteResult.Failure -> {
                    // This also runs on the main thread, safe to show a Toast
                    Toast.makeText(context, deleteResult.message, Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
            Log.d(TAG, "paymentDone: End")
            loading.value = false
            _paymentDone.emit(true)
        }
    }


    fun fetchRequests(customerId: String) {
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.IO) {
                // Database credentials and URL
                val databaseUrl =
                    "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/road_rescue"
                val databaseUser = "admin"
                val databasePassword = "admin123"

                try {
                    Class.forName("com.mysql.jdbc.Driver")
                    DriverManager.getConnection(databaseUrl, databaseUser, databasePassword)
                        .use { connection ->
                            connection.createStatement().use { statement ->
                                val resultSet = statement.executeQuery(
                                    "SELECT *, CONVERT_TZ(request_timestamp, '+00:00', '+05:30') AS request_timestamp_ist " +
                                            "FROM service_request " +
                                            "JOIN vehicle_model ON vehicle_model.id = service_request.vehicle_model_id " +
                                            "JOIN service_provider ON service_provider.id = service_request.assigned_service_provider_id " +
                                            "JOIN issue_category ON issue_category.id = service_request.issue_category_id " +
                                            "WHERE service_request.customer_id = $customerId AND assigned_service_provider_id IS NOT NULL " +
                                            "ORDER BY request_timestamp DESC;"
                                )
                                while (resultSet.next()) {
                                    requests.add(resultSetToRequest(resultSet))
                                }
                            }
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            loading.value = false
        }
    }

    fun clearRequests() {
        this.requests.clear()
    }

    fun clearData() {
        issue.value = Issues("", "", "")
        vehicleModel.value = VehicleModel("", "")
        description.value = ""
    }

    private fun resultSetToRequest(rs: ResultSet): ServiceRequest {
        return ServiceRequest(
            id = rs.getString("id"),
            customerId = rs.getString("customer_id"),
            issueCategoryId = rs.getString("issue_category_id"),
            vehicleTypeId = rs.getString("vehicle_type_id"),
            vehicleMakeId = rs.getString("vehicle_make_id"),
            vehicleModelId = rs.getString("vehicle_model_id"),
            fuelTypeId = rs.getString("fuel_type_id"),
            description = rs.getString("description"),
            status = rs.getString("status"),
            location = rs.getString("location"),
            paidAmount = rs.getString("paid_amount"),
            rating = rs.getString("rating"),
            date = rs.getString("request_timestamp_ist"),
            vehicleModelName = rs.getString("model"),
            serviceProviderName = rs.getString("garage_name"),
            issueCategoryName = rs.getString("category"),
            approxCost = rs.getString("approx_cost"),
            reqAmount = rs.getString("requested_amount")
        )
    }

    fun fetchRequestCount(customerId: String) {
        var requestCount: Int
        viewModelScope.launch {
            loading.value = true

            withContext(Dispatchers.IO) {
                val databaseUrl =
                    "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/road_rescue"
                val databaseUser = "admin"
                val databasePassword = "admin123"

                try {
                    Class.forName("com.mysql.jdbc.Driver")
                    DriverManager.getConnection(databaseUrl, databaseUser, databasePassword)
                        .use { connection ->
                            connection.createStatement().use { statement ->
                                val resultSet =
                                    statement.executeQuery("SELECT COUNT(*) AS request_count FROM service_request WHERE customer_id = '$customerId' AND assigned_service_provider_id IS NOT NULL")
                                if (resultSet.next()) {
                                    requestCount = resultSet.getInt("request_count")
                                } else {
                                    requestCount = 0
                                }
                                _requestCount.value = requestCount
                            }
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            loading.value = false
        }
    }

    val vehicleTypes = mutableStateOf(listOf<VehicleType>())
    val fuelTypes = mutableStateOf(listOf<FuelType>())
    val vehicleMakes = mutableStateOf(listOf<VehicleMake>())
    val vehicleModels = mutableStateOf(listOf<VehicleModel>())
    val issues = mutableStateOf(listOf<Issues>())

    fun fetchVehicleTypes() {

        viewModelScope.launch {
            loading.value = true
            val fetchedVehicleTypes = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getVehicleTypesFromDatabase()
            }
            vehicleTypes.value = fetchedVehicleTypes
            loading.value = false
        }
    }


    fun checkForStatus(requestId : Int) {

        viewModelScope.launch {
            loading.value = true
            val status = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                checkRequestStatus(requestId)
            }
            _status.emit(status)
            loading.value = false
        }
    }

    fun fetchIssues() {
        viewModelScope.launch {
            loading.value = true
            val fetchedIssues = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getIssuesFromDatabase()
            }
            issues.value = fetchedIssues
            loading.value = false
        }
    }

    fun fetchFuelTypes() {
        viewModelScope.launch {
            loading.value = true
            val fetchedFuelTypes = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getFuelTypesFromDatabase()
            }
            fuelTypes.value = fetchedFuelTypes
            loading.value = false
        }
    }

    fun fetchVehicleMakes() {
        viewModelScope.launch {
            loading.value = true
            val fetchedVehicleMakes = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getVehicleMakesFromDatabase()
            }
            vehicleMakes.value = fetchedVehicleMakes
            loading.value = false
        }
    }

    fun fetchVehicleModels() {
        viewModelScope.launch {
            loading.value = true
            val fetchedVehicleModels = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getVehicleModelsFromDatabase()
            }
            vehicleModels.value = fetchedVehicleModels
            loading.value = false
        }
    }

    private fun deleteRequestFromDatabase(context: Context, requestId: Int): DeleteResult {
        try {
            val DATABASE_NAME = "road_rescue"
            val TABLE_NAME = "service_request"
            val url =
                "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
            val username = "admin"
            val databasePassword = "admin123"

            Class.forName("com.mysql.jdbc.Driver")
            DriverManager.getConnection(url, username, databasePassword).use { connection ->
//                val requestId = AppPreferences(context).getStringPreference("REQUEST_ID").toInt()
                val customerId = AppPreferences(context).getStringPreference("CUSTOMER_ID").toInt()

                val statusQuery = "SELECT status, customer_Id FROM $TABLE_NAME WHERE id = ?"
                connection.prepareStatement(statusQuery).use { preparedStatement ->
                    preparedStatement.setInt(1, requestId)
                    preparedStatement.executeQuery().use { resultSet ->
                        if (resultSet.next()) {
                            val status = resultSet.getInt("status")
                            val fetchedCustomerId = resultSet.getInt("customer_Id")
                            if (status == 1 && fetchedCustomerId == customerId) {
                                val deleteQuery = "DELETE FROM $TABLE_NAME WHERE id = ?"
                                connection.prepareStatement(deleteQuery).use { deleteStatement ->
                                    deleteStatement.setInt(1, requestId)
                                    deleteStatement.executeUpdate()
                                }
                                return DeleteResult.Success
                            } else {
                                return DeleteResult.Failure("Request can not be cancelled")
                            }
                        } else {
                            return DeleteResult.Failure("No matching request found")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DeleteResult.Failure("Error cancelling request")
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun rateOrSkipDatabase(context: Context, rate: Int, requestId: Int): DeleteResult {
        try {
            val DATABASE_NAME = "road_rescue"
            val TABLE_NAME = "service_request"
            val url =
                "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
            val username = "admin"
            val databasePassword = "admin123"

            Class.forName("com.mysql.jdbc.Driver")
            DriverManager.getConnection(url, username, databasePassword).use { connection ->

                val statusQuery = if (rate == -1) {
                    "UPDATE service_request SET status = 5 WHERE service_request.id = ?"
                } else {
                    "UPDATE service_request SET status = 5, rating = ? WHERE service_request.id = ?"
                }

                connection.prepareStatement(statusQuery).use { preparedStatement ->

                    if (rate == -1) {
                        preparedStatement.setInt(1, requestId)
                    } else {
                        preparedStatement.setInt(1, rate)
                        preparedStatement.setInt(2, requestId)
                    }

                    val id = preparedStatement.executeUpdate()

//                        .use { resultSet ->
//
//
//                        if (resultSet.next()) {
                    return DeleteResult.Success
//                        } else {
//                            return DeleteResult.Failure("No matching request found")
//                        }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DeleteResult.Failure("Error cancelling request")
        }
    }

    private fun paymentDoneDatabase(requestId: Int): DeleteResult {
        try {
            val DATABASE_NAME = "road_rescue"
            val TABLE_NAME = "service_request"
            val url =
                "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
            val username = "admin"
            val databasePassword = "admin123"

            Class.forName("com.mysql.jdbc.Driver")
            DriverManager.getConnection(url, username, databasePassword).use { connection ->

                val statusQuery =
                    "UPDATE service_request SET status = 4 WHERE service_request.id = ?"

                connection.prepareStatement(statusQuery).use { preparedStatement ->
                    preparedStatement.setInt(1, requestId)

                    preparedStatement.executeUpdate()
                    return DeleteResult.Success
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DeleteResult.Failure("Error")
        }
    }

    sealed class DeleteResult {
        data object Success : DeleteResult()
        data class Failure(val message: String) : DeleteResult()
    }

    private fun getIssuesFromDatabase(): List<Issues> {
        val issueList = mutableListOf<Issues>()
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
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM issue_category")

            while (resultSet.next()) {
                val category = resultSet.getString("category")
                val id = resultSet.getString("id")
                val cost = resultSet.getString("approximated_cost")
                issueList.add(Issues(id, category, cost))
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return issueList
    }


    private fun checkRequestStatus(requestId : Int): Int {
        var status  = 0
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
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM service_request WHERE service_request.id = $requestId")

            while (resultSet.next()) {
                val st = resultSet.getInt("status")
                status = st
            }
            connection.close()
            return status
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return status
    }

    private fun getVehicleTypesFromDatabase(): List<VehicleType> {
        val vehicleTypeList = mutableListOf<VehicleType>()
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
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM vehicle_type")

            while (resultSet.next()) {
                val vehicleType = resultSet.getString("vehicle_type")
                val id = resultSet.getString("id")
                vehicleTypeList.add(VehicleType(id, vehicleType))
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return vehicleTypeList
    }

    private fun getFuelTypesFromDatabase(): List<FuelType> {
        val fuelTypeList = mutableListOf<FuelType>()
        try {
            val DATABASE_NAME = "road_rescue"
            val url = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/" +
                    DATABASE_NAME
            val username = "admin"
            val databasePassword = "admin123"

            Class.forName("com.mysql.jdbc.Driver")
            val connection: Connection =
                DriverManager.getConnection(url, username, databasePassword)
            val statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM fuel_type")

            while (resultSet.next()) {
                val fuelType = resultSet.getString("fuel_type")
                val id = resultSet.getString("id")
                fuelTypeList.add(FuelType(id, fuelType))
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return fuelTypeList
    }

    private fun getVehicleMakesFromDatabase(): List<VehicleMake> {
        val vehicleMakeList = mutableListOf<VehicleMake>()
        try {
            val DATABASE_NAME = "road_rescue"
            val url = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/" +
                    DATABASE_NAME
            val username = "admin"
            val databasePassword = "admin123"

            Class.forName("com.mysql.jdbc.Driver")
            val connection: Connection =
                DriverManager.getConnection(url, username, databasePassword)
            val statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery("SELECT * FROM vehicle_make")

            while (resultSet.next()) {
                val vehicleMake = resultSet.getString("make")
                val id = resultSet.getString("id")
                vehicleMakeList.add(VehicleMake(id, vehicleMake))
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return vehicleMakeList
    }

    private fun getVehicleModelsFromDatabase(): List<VehicleModel> {
        val vehicleModelList = mutableListOf<VehicleModel>()
        try {
            val DATABASE_NAME = "road_rescue"
            val url = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/" +
                    DATABASE_NAME
            val username = "admin"
            val databasePassword = "admin123"

            Class.forName("com.mysql.jdbc.Driver")
            val connection: Connection =
                DriverManager.getConnection(url, username, databasePassword)
            val statement = connection.createStatement()
            val resultSet: ResultSet = statement.executeQuery(
                "SELECT * " +
                        "FROM vehicle_model"
            )
            while (resultSet.next()) {
                val vehicleModel = resultSet.getString("model")
                val id = resultSet.getString("id")
                vehicleModelList.add(VehicleModel(id, vehicleModel))
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return vehicleModelList
    }
}

//"SELECT model " +
//"FROM vehicle_model " +
//"INNER JOIN vehicle_make ON vehicle_model.vehicle_make_id = vehicle_make.id " +
//"INNER JOIN vehicle_type ON vehicle_model.vehicle_type_id = vehicle_type.id " +
//"INNER JOIN fuel_type ON vehicle_model.fuel_type_id = fuel_type.id " +
//"WHERE vehicle_make.make = ${vehicleMake.value} " +
//"AND vehicle_type.vehicle_type = ${vehicleType.value} " +
//"AND fuel_type.fuel_type = ${fuelType.value};"