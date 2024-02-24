package eu.tutorials.roadrescuecustomer.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.ServiceRequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class ServiceRequestViewModel : ViewModel() {
    private val _repository: ServiceRequestRepository = ServiceRequestRepository()

    private val _issue = mutableStateOf(_repository.getServiceRequest().issue)
    private val _vehicleType = mutableStateOf(_repository.getServiceRequest().vehicleType)
    private val _fuelType = mutableStateOf(_repository.getServiceRequest().fuelType)
    private val _vehicleMake = mutableStateOf(_repository.getServiceRequest().vehicleMake)
    private val _vehicleModel = mutableStateOf(_repository.getServiceRequest().vehicleModel)
    private val _approximatedCost = mutableDoubleStateOf(_repository.getServiceRequest().approximatedCost)
    private val _description = mutableStateOf(_repository.getServiceRequest().description)

    val issue: MutableState<String> = _issue
    val vehicleType: MutableState<String> = _vehicleType
    val fuelType: MutableState<String> = _fuelType
    val vehicleMake: MutableState<String> = _vehicleMake
    val vehicleModel: MutableState<String> = _vehicleModel
    val approximatedCost: MutableState<Double> = _approximatedCost
    val description: MutableState<String> = _description

    fun setServiceRequest(issue: String, vehicleType: String, fuelType: String, vehicleMake: String, vehicleModel: String, approximatedCost: Double, description: String) {
        _repository.setServiceRequest(issue, vehicleType, fuelType, vehicleMake, vehicleModel, approximatedCost, description)
        _issue.value = _repository.getServiceRequest().issue
        _vehicleType.value = _repository.getServiceRequest().vehicleType
        _fuelType.value = _repository.getServiceRequest().fuelType
        _vehicleMake.value = _repository.getServiceRequest().vehicleMake
        _vehicleModel.value = _repository.getServiceRequest().vehicleModel
        _approximatedCost.doubleValue = _repository.getServiceRequest().approximatedCost
        _description.value = _repository.getServiceRequest().description
    }

    val vehicleTypes = mutableStateOf(listOf<String>())
    val fuelTypes = mutableStateOf(listOf<String>())
    val vehicleMakes = mutableStateOf(listOf<String>())

    fun fetchVehicleTypes() {
        viewModelScope.launch {
            val fetchedVehicleTypes = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getVehicleTypesFromDatabase()
            }
            vehicleTypes.value = fetchedVehicleTypes
        }
    }

    fun fetchFuelTypes() {
        viewModelScope.launch {
            val fetchedFuelTypes = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getFuelTypesFromDatabase()
            }
            fuelTypes.value = fetchedFuelTypes
        }
    }

    fun fetchVehicleMakes() {
        viewModelScope.launch {
            val fetchedVehicleMakes = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getVehicleMakesFromDatabase()
            }
            vehicleMakes.value = fetchedVehicleMakes
        }
    }

    private fun getVehicleTypesFromDatabase(): List<String> {
        val vehicleTypeList = mutableListOf<String>()
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
            val resultSet: ResultSet = statement.executeQuery("SELECT vehicle_type FROM vehicle_type")

            while (resultSet.next()) {
                val vehicleType = resultSet.getString("vehicle_type")
                vehicleTypeList.add(vehicleType)
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return vehicleTypeList
    }

    private fun getFuelTypesFromDatabase(): List<String> {
        val fuelTypeList = mutableListOf<String>()
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
            val resultSet: ResultSet = statement.executeQuery("SELECT fuel_type FROM fuel_type")

            while (resultSet.next()) {
                val vehicleType = resultSet.getString("fuel_type")
                fuelTypeList.add(vehicleType)
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return fuelTypeList
    }

    private fun getVehicleMakesFromDatabase(): List<String> {
        val vehicleMakeList = mutableListOf<String>()
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
            val resultSet: ResultSet = statement.executeQuery("SELECT make FROM vehicle_make")

            while (resultSet.next()) {
                val vehicleType = resultSet.getString("make")
                vehicleMakeList.add(vehicleType)
            }
            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return vehicleMakeList
    }
}

