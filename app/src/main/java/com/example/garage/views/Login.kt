package com.example.garage.views

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
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
import com.example.garage.R
import com.example.garage.ui.theme.Pink40
import com.example.garage.ui.theme.Pink80
import com.example.garage.ui.theme.Purple40
import com.example.garage.ui.theme.Purple80
import com.example.garage.ui.theme.PurpleGrey40
import com.example.garage.ui.theme.PurpleGrey80
import com.example.garage.viewModels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)


private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)



@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {


    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Header(menuClicked = {})

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

                LoginBox(navController = navController, loginViewModel =loginViewModel )//LoginBox(navController, context, loginViewModel)
            }
        }
        Footer(navController, "")
    }
}



@Composable
fun LoginBox(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current
//    var loginResponse: LoginResponse? = null
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
//                    if (phoneNumber.isNotEmpty()) {
//                        loginViewModel.checkPhoneNumberExists(
//                            Customer(
//                                null,
//                                null, null,
//                                phoneNumber = phoneNumber
//                            ), object :
//                                LoginViewModel.PhoneNumberCheckCallback {
//                                override fun onResult(exists: Boolean) {
//                                    if (exists) {
//                                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                                            phoneNumber.replace(" ", ""),  // Phone number to verify
//                                            60,  // Timeout duration
//                                            TimeUnit.SECONDS,  // Unit of timeout
////                                            mainActivity,  // Activity (for callback binding)
//                                            object :
//                                                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                                override fun onCodeSent(
//                                                    s: String,
//                                                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
//                                                ) {
//                                                    otpid = s
//                                                    Toast.makeText(
//                                                        context,
//                                                        "OTP SENT",
//                                                        Toast.LENGTH_SHORT
//                                                    )
//                                                        .show()
//                                                }
//
//                                                override fun onVerificationCompleted(
//                                                    phoneAuthCredential: PhoneAuthCredential
//                                                ) {
//                                                    mAuth?.signInWithCredential(phoneAuthCredential)
//                                                        ?.addOnCompleteListener(
//                                                            mainActivity
//                                                        ) { task ->
//                                                            if (task.isSuccessful) {
//                                                                navController.navigate("dashboardscreen")
//                                                            } else {
//                                                                Toast.makeText(
//                                                                    context,
//                                                                    "Error",
//                                                                    Toast.LENGTH_SHORT
//                                                                ).show()
//                                                            }
//                                                        }
//                                                }
//
//                                                override fun onVerificationFailed(e: FirebaseException) {
//                                                    Toast.makeText(
//                                                        context,
//                                                        e.message,
//                                                        Toast.LENGTH_LONG
//                                                    ).show()
//
//
//                                                }
//                                            })
//                                    } else {
//                                        MainScope().launch {
//                                            Toast.makeText(
//                                                context,
//                                                "User is not Registered",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                        }
//                                    }
//                                }
//                            })
//                    } else {
//                        Toast.makeText(context, "Enter the Phone Number", Toast.LENGTH_SHORT).show()
//                    }
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
                // handle otp
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