package eu.tutorials.roadrescuecustomer.models

import android.content.Context
import android.widget.Toast
import eu.tutorials.roadrescuecustomer.AppPreferences
import eu.tutorials.roadrescuecustomer.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Profile(
    var name: String,
    var email: String?,
    var phoneNumber: String,
    var numOfServiceRequests: Int
)

class ProfileRepository() {

    private var _profile = Profile(
        "Nirmal Hettiarachchi",
        "nirmalhettiarachchi@gmail.com",
        "+94 768879830",
        2
    )

    fun getProfile() = _profile

    fun updateProfile(name: String, email: String, context: Context) {
        val apiService = RetrofitInstance.apiService
        val call = apiService.update_profile(
            AppPreferences(context).getStringPreference("PHONE", ""),
            name, email
        )
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        context,
                        response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    AppPreferences(context).setStringPreference("NAME", name)
                    AppPreferences(context).setStringPreference(
                        "EMAIL",
                        email
                    )
                } else {
                    Toast.makeText(
                        context,
                        response.message().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    t.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}