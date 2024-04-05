package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.FuelType
import eu.tutorials.roadrescuecustomer.models.Issues
import eu.tutorials.roadrescuecustomer.models.RequestModel
import eu.tutorials.roadrescuecustomer.models.ServiceRequestRepository
import eu.tutorials.roadrescuecustomer.models.VehicleMake
import eu.tutorials.roadrescuecustomer.models.VehicleModel
import eu.tutorials.roadrescuecustomer.models.VehicleType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class ServiceRequestViewModel : ViewModel() {
    private val _repository: ServiceRequestRepository = ServiceRequestRepository()

    private val _issue = mutableStateOf(Issues("", "", ""))
    private val indicator = mutableStateOf(false)
    private val _vehicleType = mutableStateOf(VehicleType("", ""))
    private val _fuelType = mutableStateOf(FuelType("", ""))
    private val _vehicleMake = mutableStateOf(VehicleMake("", ""))
    private val _vehicleModel = mutableStateOf(VehicleModel("", ""))
    private val _description = mutableStateOf(_repository.getServiceRequest().description)

    val issue: MutableState<Issues> = _issue
    val indicator1: MutableState<Boolean> = indicator
    val indicator2: MutableState<Boolean> = indicator
    val indicator3: MutableState<Boolean> = indicator
    val indicator4: MutableState<Boolean> = indicator
    val indicator5: MutableState<Boolean> = indicator
    val indicator6: MutableState<Boolean> = indicator
    val vehicleType: MutableState<VehicleType> = _vehicleType
    val fuelType: MutableState<FuelType> = _fuelType
    val vehicleMake: MutableState<VehicleMake> = _vehicleMake
    val vehicleModel: MutableState<VehicleModel> = _vehicleModel
    val description: MutableState<String> = _description

    fun setServiceRequest(
        context: Context,
        requestModel: RequestModel, requestCallback: ServiceRequestRepository.RequestCallback
    ) {
        _repository.requestService(
            context,
            requestModel,
            requestCallback
        )
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
    fun deleteRequest(
        context: Context,
        requestCallback: ServiceRequestRepository.RequestCallback
    ) {
        _repository.deleteRequest(
            context,
            requestCallback
        )
    }

    val vehicleTypes = mutableStateOf(listOf<VehicleType>())
    val fuelTypes = mutableStateOf(listOf<FuelType>())
    val vehicleMakes = mutableStateOf(listOf<VehicleMake>())
    val vehicleModels = mutableStateOf(listOf<VehicleModel>())
    val issues = mutableStateOf(listOf<Issues>())

    fun fetchVehicleTypes() {
        viewModelScope.launch {
            val fetchedVehicleTypes = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getVehicleTypesFromDatabase()
            }
            vehicleTypes.value = fetchedVehicleTypes
        }
    }

    fun fetchIssues() {
        viewModelScope.launch {
            val fetchedIssues = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getIssuesFromDatabase()
            }
            issues.value = fetchedIssues
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

    fun fetchVehicleModels() {
        viewModelScope.launch {
            val fetchedVehicleModels = withContext(Dispatchers.IO) {
                // Actual database operation to fetch vehicle types
                getVehicleModelsFromDatabase()
            }
            vehicleModels.value = fetchedVehicleModels
        }
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