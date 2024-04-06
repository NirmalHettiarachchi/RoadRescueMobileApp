package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.models.AddUserCallback
import eu.tutorials.roadrescuecustomer.models.Customer
import eu.tutorials.roadrescuecustomer.models.SingUpRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _repository: SingUpRepository = SingUpRepository()

    fun addUser(
        user: Customer,
        navController: NavHostController,
        context: Context
    ) {
        _repository.addUser(
            context,
            user,
            object : AddUserCallback {
                override fun onUserAddedSuccessfully(id: String) {
                    AppPreferences(context).setStringPreference(
                        "NAME",
                        "${user.fName} ${user.lName}"
                    )
                    user.phoneNumber?.let {
                        AppPreferences(context).setStringPreference(
                            "PHONE",
                            it
                        )
                    }
                    AppPreferences(context).setStringPreference(
                        "EMAIL",
                        ""
                    )
                    AppPreferences(context).setStringPreference(
                        "CUSTOMER_ID",
                        id
                    )
                    navController.navigate("dashboardscreen") {
                        popUpTo("dashboardscreen") { inclusive = true }
                    }
                }

                override fun onUserAlreadyExists() {
                    MainScope().launch {
                        Toast.makeText(
                            context,
                            "User Already Exist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(errorMessage: String) {
                    MainScope().launch {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }
}