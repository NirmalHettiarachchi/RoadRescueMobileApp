package eu.tutorials.roadrescuecustomer.models

import android.content.Context
import android.widget.Toast
import eu.tutorials.roadrescuecustomer.AppPreferences
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager

data class Profile(
    var name: String,
    var email: String?,
    var phoneNumber: String,
    var numOfServiceRequests: Int
)

class ProfileRepository() {

    private var _profile = Profile(
        "",
        "",
        "",
        2
    )

    fun getProfile() = _profile

    fun updateProfile(phoneNumber: String, name: String, email: String, context: Context) {
        val DATABASE_NAME = "road_rescue"
        val TABLE_NAME = "customer"
        val url =
            "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        val username = "admin"
        val databasePassword = "admin123"

        Thread {
            try {
                Class.forName("com.mysql.jdbc.Driver")
                val connection: Connection =
                    DriverManager.getConnection(url, username, databasePassword)
                val names = name.split(" ")
                val firstName = names.firstOrNull() ?: ""
                val lastName = names.drop(1).joinToString(" ")
                val updateStmt =
                    connection.prepareStatement("UPDATE $TABLE_NAME SET f_name = ?, l_name = ?, email = ? WHERE phone_number = ?")
                updateStmt.setString(1, firstName)
                updateStmt.setString(2, lastName)
                updateStmt.setString(3, email)
                updateStmt.setString(4, phoneNumber)

                val rowsAffected = updateStmt.executeUpdate()

                // Check if any rows were affected to determine if the update was successful
                val success = rowsAffected > 0
                AppPreferences(context).setStringPreference("NAME", name)
                AppPreferences(context).setStringPreference(
                    "EMAIL",
                    email
                )
                // Use the callback to return the result
                MainScope().launch {
                    Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                connection.close()
            } catch (e: Exception) {
                e.printStackTrace()
                MainScope().launch {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()

    }
}