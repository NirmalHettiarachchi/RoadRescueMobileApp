package eu.tutorials.roadrescuecustomer.models

import android.content.Context
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager


data class Customer(
    var fName: String?,
    var lName: String?,
    var email: String?,
    var phoneNumber: String?
)

interface AddUserCallback {
    fun onUserAddedSuccessfully(id: String)
    fun onUserAlreadyExists()
    fun onError(errorMessage: String)
}

class SingUpRepository() {
    fun addUser(
        context: Context,
        user: Customer, callback: AddUserCallback
    ) {
        val DATABASE_NAME = "road_rescue"
        val TABLE_NAME = "customer"
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

                // Prepare a statement to check if the phone number already exists
                val checkStmt =
                    connection.prepareStatement("SELECT COUNT(*) FROM $TABLE_NAME WHERE phone_number = ?")
                checkStmt.setString(1, user.phoneNumber)

                // Execute the query
                val resultSet = checkStmt.executeQuery()

                // Check if the phone number exists
                var phoneNumberExists = false
                if (resultSet.next()) {
                    phoneNumberExists = resultSet.getInt(1) > 0
                }

                // Insert the new user if the phone number does not exist
                if (!phoneNumberExists) {
                    val insertStmt =
                        connection.prepareStatement("INSERT INTO $TABLE_NAME(phone_number, email, f_name, l_name) VALUES(?, ?, ?, ?)")
                    insertStmt.setString(1, user.phoneNumber)
                    insertStmt.setString(2, user.email)
                    insertStmt.setString(3, user.fName)
                    insertStmt.setString(4, user.lName)

                    insertStmt.executeUpdate()
                    MainScope().launch {
                        callback.onUserAddedSuccessfully(resultSet.getInt("id").toString())
                    }
                } else {
                    MainScope().launch {
                        callback.onUserAlreadyExists()
                    }
                }
                // Close the connection
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