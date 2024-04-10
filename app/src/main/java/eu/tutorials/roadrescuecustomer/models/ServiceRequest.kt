package eu.tutorials.roadrescuecustomer.models

import android.content.Context
import android.widget.Toast
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

data class ServiceRequest(
    var id: String,
    var customerId: String,
    var issueCategoryId: String,
    var indicator1: Boolean = false,
    var indicator2: Boolean = false,
    var indicator3: Boolean = false,
    var indicator4: Boolean = false,
    var indicator5: Boolean = false,
    var indicator6: Boolean = false,
    var vehicleTypeId: String,
    var vehicleMakeId: String,
    var vehicleModelId: String,
    var fuelTypeId:String,
    var description: String,
    var status: String,
    var location: String,
    var paidAmount: String,
    var rating: String,
    var date: String = ""
)

class ServiceRequestRepository {

    private var _serviceRequest = ServiceRequest(id = "", customerId = "", issueCategoryId = "", vehicleTypeId = "", vehicleMakeId = "",
        vehicleModelId = "", fuelTypeId = "", description = "", status = "", location = "", paidAmount = "", rating = "")

    fun getServiceRequest() = _serviceRequest

    interface RequestCallback {
        fun success(id: String)
        fun onError(errorMessage: String)
    }

    fun requestService(
        context: Context,
        serviceRequest: ServiceRequest,
        callback: RequestCallback
    ) {
        val DATABASE_NAME = "road_rescue"
        val TABLE_NAME = "service_request"
        val url =
            "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        val username = "admin"
        val databasePassword = "admin123"
        Thread {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.jdbc.Driver")
                // Establish connection to the database
                val connection: Connection =
                    DriverManager.getConnection(url, username, databasePassword)

                val insertStmt =
                    connection.prepareStatement(
                        "INSERT INTO $TABLE_NAME(customer_id, issue_category_id,indicator_1,indicator_2,indicator_3,indicator_4,indicator_5,indicator_6,vehicle_type_id,vehicle_make_id,vehicle_model_id,fuel_type_id,description,status,location,paid_amount,rating) VALUES(?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                    )
                AppPreferences(context).getStringPreference("CUSTOMER_ID", "").toIntOrNull()?.let {
                    insertStmt.setInt(
                        1,
                        it
                    )
                }
                insertStmt.setInt(2, serviceRequest.issueCategoryId.toInt())
                insertStmt.setBoolean(3, serviceRequest.indicator1)
                insertStmt.setBoolean(4, serviceRequest.indicator2)
                insertStmt.setBoolean(5, serviceRequest.indicator3)
                insertStmt.setBoolean(6, serviceRequest.indicator4)
                insertStmt.setBoolean(7, serviceRequest.indicator5)
                insertStmt.setBoolean(8, serviceRequest.indicator6)
                insertStmt.setInt(9, serviceRequest.vehicleTypeId.toInt())
                insertStmt.setInt(10, serviceRequest.vehicleMakeId.toInt())
                insertStmt.setInt(11, serviceRequest.vehicleModelId.toInt())
                insertStmt.setInt(12, serviceRequest.fuelTypeId.toInt())
                insertStmt.setString(13, serviceRequest.description)
                insertStmt.setInt(14, 1)
                insertStmt.setString(15, serviceRequest.location)
                insertStmt.setDouble(16, 0.0)
                insertStmt.setInt(17, 5)

                insertStmt.executeUpdate()

                val rs = insertStmt.generatedKeys
                if (rs.next()) {
                    val generatedId = rs.getInt(1)
                    MainScope().launch {
                        AppPreferences(context).setStringPreference(
                            "REQUEST_ID",
                            generatedId.toString()
                        )
                        callback.success("$generatedId")
                    }
                } else {
                    // Handle the case where no keys were generated or returned
                    MainScope().launch {
                        callback.success("")
                    }
                }
                connection.close()
            } catch (e: Exception) {
                MainScope().launch {
                    callback.onError(e.message ?: "An error occurred.")
                }
                e.printStackTrace()
            }
        }.start()
    }

    fun checkRequest(
        context: Context,
        callback: RequestCallback
    ) {
        val DATABASE_NAME = "road_rescue"
        val TABLE_NAME = "service_request"
        val url =
            "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        val username = "admin"
        val databasePassword = "admin123"
        Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                val connection: Connection =
                    DriverManager.getConnection(url, username, databasePassword)

                val statusQuery = "SELECT status FROM $TABLE_NAME WHERE id = ?"
                val preparedStatement = connection.prepareStatement(statusQuery)
                preparedStatement.setInt(
                    1,
                    AppPreferences(context).getStringPreference("REQUEST_ID").toInt()
                )

                preparedStatement.executeQuery()

                val resultSet = preparedStatement.executeQuery()
                if (resultSet.next()) {
                    val status = resultSet.getInt("status")
                    MainScope().launch {
                        callback.success(status.toString())
                    }
                } else {
                    MainScope().launch {
                        Toast.makeText(context, "No Status", Toast.LENGTH_SHORT).show()
                    }
                }
                connection.close()
            } catch (e: Exception) {
                MainScope().launch {
                    callback.onError(e.message ?: "An error occurred.")
                }
                e.printStackTrace()
            }
        }.start()
    }
}
