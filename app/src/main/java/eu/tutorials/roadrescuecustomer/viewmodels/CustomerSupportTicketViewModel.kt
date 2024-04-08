import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.CustomerSupportTicket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.DriverManager
import java.sql.ResultSet

class CustomerSupportTicketViewModel : ViewModel() {

    val tickets = mutableStateListOf<CustomerSupportTicket>()
    val loading = mutableStateOf(false)

    fun fetchCustomerSupportTickets(customerId: String) {
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.IO) {
                // Database credentials and URL
                val databaseUrl = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/road_rescue"
                val databaseUser = "admin"
                val databasePassword = "admin123"

                try {
                    Class.forName("com.mysql.jdbc.Driver")
                    DriverManager.getConnection(databaseUrl, databaseUser, databasePassword).use { connection ->
                        connection.createStatement().use { statement ->
                            val resultSet = statement.executeQuery("SELECT * FROM customer_support_ticket where customer_id = $customerId")
                            while (resultSet.next()) {
                                tickets.add(resultSetToTicket(resultSet))
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle exceptions
                }
            }
            loading.value = false
        }
    }

    fun clearTickets() {
        this.tickets.clear()
    }

    private fun resultSetToTicket(rs: ResultSet): CustomerSupportTicket {
        return CustomerSupportTicket(
            id = rs.getInt("id"), // Adjust field names based on your database
            customerId = rs.getInt("customer_id"),
            issue = rs.getString("title"),
            date = rs.getString("created_time"),
            status = rs.getString("status"),
            description = rs.getString("description"),
            solution = rs.getString("solution")
        )
    }

    fun writeCustomerSupportTicket(customerId: Int, issue: String, description: String) {
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.IO) {
                // Database credentials and URL
                val databaseUrl = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/road_rescue"
                val databaseUser = "admin"
                val databasePassword = "admin123"

                try {
                    Class.forName("com.mysql.jdbc.Driver")
                    DriverManager.getConnection(databaseUrl, databaseUser, databasePassword).use { connection ->
                        val preparedStatement = connection.prepareStatement(
                            "INSERT INTO customer_support_ticket (customer_id, title, description, status, solution) VALUES (?, ?, ?, 'pending', '')"
                        ).apply {
                            setInt(1, customerId)
                            setString(2, issue)
                            setString(3, description)
                        }
                        preparedStatement.executeUpdate()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle exceptions
                }
            }
            loading.value = false
        }
    }

}