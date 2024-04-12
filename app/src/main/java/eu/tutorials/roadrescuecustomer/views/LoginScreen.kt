package eu.tutorials.roadrescuecustomer.views

import android.util.Log
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
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.Customer
import eu.tutorials.roadrescuecustomer.models.LoginResponse
import eu.tutorials.roadrescuecustomer.viewmodels.LoginViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@Composable
fun LoginScreen(
    navController: NavHostController,
    context: MainActivity,
    loginViewModel: LoginViewModel
) {
    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
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
                Spacer(modifier = Modifier.height(12.dp))
                LoginBox(navController, context, loginViewModel)
            }
        }
    }
}

@Composable
fun LoginBox(
    navController: NavHostController,
    mainActivity: MainActivity,
    loginViewModel: LoginViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current
    var loginResponse: LoginResponse? = null
    var mAuth: FirebaseAuth? = null
    mAuth = FirebaseAuth.getInstance()
    var otpid by remember {
        mutableStateOf("")
    }


    var loading by remember { mutableStateOf(false) }


    CircularProgressBar(isDisplayed = loading)


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
            phoneNumber = AuthField("Registered Phone Number", "", true)

            AuthFieldBtn(
                onClickButton = {
                    if (phoneNumber.isNotEmpty() && phoneNumber.length == 12 && phoneNumber.startsWith("+94")) {
//                        showLoading
                        loading = true
                        loginViewModel.checkPhoneNumberExists(
                            Customer(
                                null,
                                null, null,
                                phoneNumber = phoneNumber
                            ), object :
                                LoginViewModel.PhoneNumberCheckCallback {
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
                                                    loading = false
                                                    otpid = s
                                                    Log.d("TAG", "onCodeSent: OTP Received $s")
                                                    Toast.makeText(
                                                        context,
                                                        "OTP sent",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }

                                                override fun onVerificationCompleted(
                                                    phoneAuthCredential: PhoneAuthCredential
                                                ) {
                                                    loading = false
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
                                                    loading = false
                                                    Toast.makeText(
                                                        context,
                                                        e.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()


                                                }
                                            })
                                    } else {
                                        loading = false
                                        MainScope().launch {
                                            Toast.makeText(
                                                context,
                                                "Phone number is not registered",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            })
                    } else {
                        Toast.makeText(context, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            otp = AuthField("Enter the OTP", "", false)
            //Edit button
            AuthCommonButton(
                btnName = "Log in",
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {

                if (otp.isNotEmpty()) {
                    loading = true
                    val credential =
                        otpid.let { PhoneAuthProvider.getCredential(it, otp) }
                    if (credential != null) {
                        mAuth?.signInWithCredential(credential)
                            ?.addOnCompleteListener(
                                mainActivity
                            ) { task ->
                                if (task.isSuccessful) {
                                    loginViewModel.getUserDetails(
                                        Customer(
                                            null,
                                            null,
                                            null,
                                            phoneNumber
                                        ), object :
                                            LoginViewModel.UserRetrievalCallback {
                                            override fun onUserRetrieved(
                                                success: Boolean,
                                                firstName: String?,
                                                lastName: String?,
                                                email: String?,
                                                id: String?
                                            ) {
                                                if (success) {
                                                    loading = false
                                                    AppPreferences(context).setStringPreference(
                                                        "NAME",
                                                        "$firstName $lastName"
                                                    )
                                                    AppPreferences(context).setStringPreference(
                                                        "PHONE",
                                                        phoneNumber
                                                    )
                                                    if (id != null) {
                                                        AppPreferences(context).setStringPreference(
                                                            "CUSTOMER_ID",
                                                            id
                                                        )
                                                    }
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
                                                    loading = false
                                                    println("User not found or error occurred.")
                                                }
                                            }
                                        })
                                } else {
                                    loading = false
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }else{
                        loading = false
                        Log.d("TAG", "LoginBox: Credential Null")
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