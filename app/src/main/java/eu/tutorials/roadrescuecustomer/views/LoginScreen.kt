package eu.tutorials.roadrescuecustomer.views

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import eu.tutorials.roadrescuecustomer.AppPreferences
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.LoginResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.concurrent.TimeUnit


@Composable
fun LoginScreen(navController: NavHostController, context: MainActivity) {
    Scaffold (
        topBar = {
            AuthHeader()
        }
    ){
        Column(
            backgroundModifier.padding(it).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                backgroundModifier,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.log_in_to_road_rescue),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = textStyle1
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LoginBox(navController, context)
                    HelpBox()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

interface PhoneNumberCheckCallback {
    fun onResult(exists: Boolean)
}

fun checkPhoneNumberExists(phoneNumber: String, callback: PhoneNumberCheckCallback) {
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

            val checkStmt =
                connection.prepareStatement("SELECT COUNT(*) FROM $TABLE_NAME WHERE phone_number = ?")
            checkStmt.setString(1, phoneNumber)

            val resultSet = checkStmt.executeQuery()

            val exists = resultSet.next() && resultSet.getInt(1) > 0

            // Use the callback to return the result
            callback.onResult(exists)

            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onResult(false) // Consider how you want to handle errors
        }
    }.start()
}

interface UserRetrievalCallback {
    fun onUserRetrieved(success: Boolean, firstName: String?, lastName: String?, email: String?)
}

fun getUserDetails(phoneNumber: String, callback: UserRetrievalCallback) {
    val DATABASE_NAME = "road_rescue"
    val TABLE_NAME = "customer"
    val url =
        "jdbc:mysql://database-1.cxaiwakqecm4.eu-north-1.rds.amazonaws.com:3306/$DATABASE_NAME"
    val username = "admin"
    val databasePassword = "admin123"

    Thread {
        var connection: Connection? = null
        try {
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(url, username, databasePassword)

            val queryStmt =
                connection.prepareStatement("SELECT f_name, l_name, email FROM $TABLE_NAME WHERE phone_number = ?")
            queryStmt.setString(1, phoneNumber)

            val resultSet = queryStmt.executeQuery()

            if (resultSet.next()) {
                val firstName = resultSet.getString("f_name")
                val lastName = resultSet.getString("l_name")
                val email = resultSet.getString("email")

                // Success, user found
                callback.onUserRetrieved(true, firstName, lastName, email)
            } else {

                // User not found
                callback.onUserRetrieved(false, null, null, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onUserRetrieved(false, null, null, null) // In case of error
        } finally {
            try {
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }.start()
}

@Composable
fun LoginBox(navController: NavHostController, mainActivity: MainActivity) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current
    var loginResponse: LoginResponse? = null
    var mAuth: FirebaseAuth? = null
    mAuth = FirebaseAuth.getInstance()
    var otpid: String? = null
    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))// Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            phoneNumber = AuthField("Registered Phone Number", "")

            AuthFieldBtn(
                onClickButton = {
                    if (phoneNumber.isNotEmpty()) {
                        checkPhoneNumberExists(phoneNumber, object : PhoneNumberCheckCallback {
                            override fun onResult(exists: Boolean) {
                                if (exists) {
                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        phoneNumber.replace(" ", ""),  // Phone number to verify
                                        60,  // Timeout duration
                                        TimeUnit.SECONDS,  // Unit of timeout
                                        mainActivity,  // Activity (for callback binding)
                                        object :
                                            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            override fun onCodeSent(
                                                s: String,
                                                forceResendingToken: PhoneAuthProvider.ForceResendingToken
                                            ) {
                                                otpid = s
                                                Toast.makeText(
                                                    context,
                                                    "OTP SENT",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }

                                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                                                mAuth?.signInWithCredential(phoneAuthCredential)
                                                    ?.addOnCompleteListener(
                                                        mainActivity
                                                    ) { task ->
                                                        if (task.isSuccessful) {
                                                            navController.navigate("dashboardscreen")
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "Error",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                            }

                                            override fun onVerificationFailed(e: FirebaseException) {
                                                Toast.makeText(
                                                    context,
                                                    e.message,
                                                    Toast.LENGTH_LONG
                                                ).show()


                                            }
                                        })
                                } else {
                                    MainScope().launch {
                                        Toast.makeText(
                                            context,
                                            "User is not Registered",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        })
                    } else {
                        Toast.makeText(context, "Enter the Phone Number", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            otp = AuthField("Enter the OTP", "")
            //Edit button
            AuthCommonButton(
                btnName = "Log in",
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {
                if (otp.isNotEmpty()) {
                    val credential =
                        otpid?.let { PhoneAuthProvider.getCredential(it, otp) }
                    if (credential != null) {
                        mAuth?.signInWithCredential(credential)
                            ?.addOnCompleteListener(
                                mainActivity
                            ) { task ->
                                if (task.isSuccessful) {
                                    getUserDetails(phoneNumber, object : UserRetrievalCallback {
                                        override fun onUserRetrieved(
                                            success: Boolean,
                                            firstName: String?,
                                            lastName: String?,
                                            email: String?
                                        ) {
                                            if (success) {
                                                AppPreferences(context).setStringPreference(
                                                    "NAME",
                                                    "$firstName $lastName"
                                                )
                                                AppPreferences(context).setStringPreference(
                                                    "PHONE",
                                                    phoneNumber
                                                )
                                                if (email != null) {
                                                    AppPreferences(context).setStringPreference(
                                                        "EMAIL",
                                                        email
                                                    )
                                                }
                                                MainScope().launch {
                                                    navController.navigate("dashboardscreen") {
                                                        popUpTo("dashboardscreen") {
                                                            inclusive = true
                                                        }
                                                    }
                                                }
                                            } else {
                                                println("User not found or error occurred.")
                                            }
                                        }
                                    })
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(context, "Enter OTP", Toast.LENGTH_SHORT).show()
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Haven’t registered yet?",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            AuthCommonButton(
                btnName = "Register",
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {
                navController.navigate("signupscreen")
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}