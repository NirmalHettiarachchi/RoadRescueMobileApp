package eu.tutorials.roadrescuecustomer.models

import android.content.Context
import eu.tutorials.roadrescuecustomer.AppConfig
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
        val TABLE_NAME = "customer"
        val url = AppConfig.DATABASE_URL
        val username = AppConfig.DATABASE_USERNAME
        val databasePassword = AppConfig.DATABASE_PASSWORD
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

                resultSet.close()

                // Insert the new user if the phone number does not exist
                if (!phoneNumberExists) {
                    val insertStmt =
                        connection.prepareStatement("INSERT INTO $TABLE_NAME(phone_number, email, f_name, l_name) VALUES(?, ?, ?, ?)")
                    insertStmt.setString(1, user.phoneNumber)
                    insertStmt.setString(2, user.email)
                    insertStmt.setString(3, user.fName)
                    insertStmt.setString(4, user.lName)

                    insertStmt.executeUpdate()




                    // Prepare a statement to check if the phone number already exists
                    val checkStmt1 =
                        connection.prepareStatement("SELECT * FROM $TABLE_NAME WHERE phone_number = ?")
                    checkStmt1.setString(1, user.phoneNumber)

                    // Execute the query
                    val resultSet1 = checkStmt1.executeQuery()

                    if (resultSet1.next()) {
                        val id = resultSet1.getInt("id").toString()
                        MainScope().launch {
                            callback.onUserAddedSuccessfully(id)
                        }
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