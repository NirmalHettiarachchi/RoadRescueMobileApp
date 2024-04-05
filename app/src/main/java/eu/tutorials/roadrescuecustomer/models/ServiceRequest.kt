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
    var issue: String,
    var vehicleType: String,
    var fuelType: String,
    var vehicleMake: String,
    var vehicleModel: String,
    var approximatedCost: Double,
    var description: String,
    var status: String = "Pending"
)

class ServiceRequestRepository {

    private var _serviceRequest = ServiceRequest("", "", "", "", "", 0.00, "")

    fun getServiceRequest() = _serviceRequest

    interface RequestCallback {
        fun success(id: String)
        fun onError(errorMessage: String)
    }

    fun requestService(
        context: Context,
        requestModel: RequestModel,
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
                insertStmt.setInt(2, requestModel.issue_category_id.toInt())
                insertStmt.setBoolean(3, requestModel.indicator_1)
                insertStmt.setBoolean(4, requestModel.indicator_2)
                insertStmt.setBoolean(5, requestModel.indicator_3)
                insertStmt.setBoolean(6, requestModel.indicator_4)
                insertStmt.setBoolean(7, requestModel.indicator_5)
                insertStmt.setBoolean(8, requestModel.indicator_6)
                insertStmt.setInt(9, requestModel.vehicle_type_id.toInt())
                insertStmt.setInt(10, requestModel.vehicle_make_id.toInt())
                insertStmt.setInt(11, requestModel.vehicle_model_id.toInt())
                insertStmt.setInt(12, requestModel.fuel_type_id.toInt())
                insertStmt.setString(13, requestModel.description)
                insertStmt.setInt(14, 1)
                insertStmt.setString(15, requestModel.location)
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
                // Load the JDBC driver
                Class.forName("com.mysql.jdbc.Driver")
                // Establish connection to the database
                val connection: Connection =
                    DriverManager.getConnection(url, username, databasePassword)

                // Insert the new user if the phone number does not exist
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


    fun deleteRequest(
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
                // Load the JDBC driver
                Class.forName("com.mysql.jdbc.Driver")
                // Establish connection to the database
                val connection: Connection =
                    DriverManager.getConnection(url, username, databasePassword)

                val statusQuery = "SELECT status FROM $TABLE_NAME WHERE id = ?"
                val preparedStatement = connection.prepareStatement(statusQuery)
                preparedStatement.setInt(
                    1,
                    AppPreferences(context).getStringPreference("REQUEST_ID").toInt()
                )

                val resultSet = preparedStatement.executeQuery()
                if (resultSet.next()) {
                    val status = resultSet.getInt("status")
                    if (status != 1) {
                        // If status is not 1, delete the request_service column
                        val deleteQuery = "ALTER TABLE $TABLE_NAME DROP COLUMN request_service"
                        val deleteStatement = connection.prepareStatement(deleteQuery)
                        deleteStatement.executeUpdate()
                    }
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
