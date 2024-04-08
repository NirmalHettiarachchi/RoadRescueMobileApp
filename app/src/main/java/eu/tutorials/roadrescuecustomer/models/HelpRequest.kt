package eu.tutorials.roadrescuecustomer.models

import java.sql.Connection
import java.sql.DriverManager

data class HelpRequest(
    var category: String,
    var date: String,
    var status: String,
    var description: String,
    var solution: String,
)

object HelpRequestRepository {
    fun fetchHelpRequests(
        onSuccess: (List<HelpRequest>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val DATABASE_NAME = "road_rescue"
        val TABLE_NAME = "customer_support_ticket"
        val url = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        val username = "admin"
        val password = "admin123"
        Thread {
            var connection: Connection? = null
            try {
                Class.forName("com.mysql.jdbc.Driver")
                connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery("SELECT * FROM $TABLE_NAME")

                val helpRequests = mutableListOf<HelpRequest>()
                while (resultSet.next()) {
                    val helpRequest = HelpRequest(
                        category = resultSet.getString("title"),
                        date = resultSet.getString("created_time"),
                        status = resultSet.getString("status"),
                        description = resultSet.getString("description"),
                        solution = resultSet.getString("solution")
                    )
                    helpRequests.add(helpRequest)
                }
                onSuccess(helpRequests)
            } catch (e: Exception) {
                onError(e) // Invoke the error callback
            } finally {
                try {
                    connection?.close() // Ensure the connection is closed even in case of an exception
                } catch (e: Exception) {
                    // Optionally handle this error or log it
                }
            }
        }.start()
    }
}


//    fun addHelpRequest(helpRequest: HelpRequest) {
//        Thread {
//            try {
//                Class.forName("com.mysql.jdbc.Driver")
//                val connection: java.sql.Connection? = DriverManager.getConnection(url, username, password)
//                val sql = "INSERT INTO $TABLE_NAME (category, date, status, description, solution) VALUES (?, ?, ?, ?, ?)"
//                val statement = connection.prepareStatement(sql).apply {
//                    setString(1, helpRequest.category)
//                    setString(2, helpRequest.date)
//                    setString(3, helpRequest.status)
//                    setString(4, helpRequest.description)
//                    setString(5, helpRequest.solution)
//                }
//                statement.executeUpdate()
//                connection.close()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }.start()
//    }




