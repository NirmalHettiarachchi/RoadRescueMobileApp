package com.example.garage.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun ChangePhoneNumWindow(navController: NavController,onDismiss: () -> Unit) {

    var newPhoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current

    var loading by remember { mutableStateOf(false) }
    /*CircularProgressBar(isDisplayed = loading)*/

    var mAuth: FirebaseAuth? = null
    mAuth = FirebaseAuth.getInstance()
    var otpid by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        tonalElevation = 16.dp,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFDCE4EC)
            )
            .verticalScroll(rememberScrollState()),
        containerColor = Color(0xFFDCE4EC),
        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Change registered phone number",
                    style = textStyle2
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "(You will be logged out)",
                    style = textStyle4
                )
                Spacer(modifier = Modifier.height(16.dp))

                newPhoneNumber = AuthField("New Phone Number", "",isMobile = true)

                Spacer(modifier = Modifier.height(16.dp))

                AuthFieldBtn(
                    onClickButton = {

                        /*if (newPhoneNumber.isNotEmpty() && newPhoneNumber.trim().length == 12 && newPhoneNumber.trim().startsWith("+94")) {
                            loading = true
                            loginViewModel.checkPhoneNumberExists(
                                Customer(
                                    null,
                                    null, null,
                                    phoneNumber = newPhoneNumber
                                ), object :
                                    LoginViewModel.PhoneNumberCheckCallback {
                                    override fun onResult(exists: Boolean) {
                                        if (!exists) {
                                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                newPhoneNumber.replace(" ", ""),  // Phone number to verify
                                                60,  // Timeout duration
                                                TimeUnit.SECONDS,  // Unit of timeout
                                                //mainActivity,  // Activity (for callback binding)
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
                                                               // mainActivity
                                                            ) { task ->
                                                                if (task.isSuccessful) {
                                                                    //todo
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
                                                    "Phone number already exists",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                })
                        } else {
                            Toast.makeText(context, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
                        }*/
                    }
                )

              //  otp = AuthField("Enter the OTP", "", false)

                Spacer(modifier = Modifier.height(16.dp))

                AuthCommonButton(
                    btnName = "Confirm",
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
                                  //  mainActivity
                                ) { task ->
                                  /*  val customerId = AppPreferences(context).getStringPreference(
                                        "CUSTOMER_ID",
                                        ""
                                    )*/
                                    if (task.isSuccessful) {
                                        //profileViewModel.changePhoneNumber(newPhoneNumber, customerId)
                                        navController.navigate("loginscreen") {
                                            popUpTo("loginscreen") { inclusive = true }
                                        }
                                      //  AppPreferences(context).clearAllPreferences()
                                        /*currentStateViewModel.setCurrentState(
                                            isServiceRequested = false,
                                            isReqServiceWindowOpened = false
                                        )*/
//                                        serviceRequestViewModel.clearData()
                                        Toast.makeText(context, "Phone number changed successfully", Toast.LENGTH_SHORT).show()
                                        loading = false
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

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}