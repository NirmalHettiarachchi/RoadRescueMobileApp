package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.roadrescuecustomer.models.Tip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.DriverManager

class TipViewModel : ViewModel() {

    private val _tips = MutableStateFlow(emptyList<Tip>())
    val tips = _tips.asStateFlow()

    var loading = mutableStateOf(false)

    fun fetchTip(tipId: String) {
        viewModelScope.launch {
            loading.value = true
            val fetchedTips = withContext(Dispatchers.IO) {
                fetchTipsFromDatabase(tipId)
            }
            _tips.value = fetchedTips
            loading.value = false
        }
    }

    private fun fetchTipsFromDatabase(tipId: String): List<Tip> {
        val tips = mutableListOf<Tip>()
        val DATABASE_NAME = "road_rescue"
        val url = "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        val username = "admin"
        val databasePassword = "admin123"

        try {
            Class.forName("com.mysql.jdbc.Driver")
            DriverManager.getConnection(url, username, databasePassword).use { connection ->
                val preparedStatement = connection.prepareStatement("SELECT * FROM maintenance_tips WHERE id = ?;")
                preparedStatement.setString(1, tipId)
                val resultSet = preparedStatement.executeQuery()

                while (resultSet.next()) {
                    val id = resultSet.getString("id")
                    val tipStr = resultSet.getString("tip")
                    val description = resultSet.getString("description")

                    tips.add(Tip(id, tipStr, description))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching tips from database", e)
            e.printStackTrace()
        }
        return tips
    }
}