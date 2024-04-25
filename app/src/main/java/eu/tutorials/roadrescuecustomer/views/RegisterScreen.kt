package eu.tutorials.roadrescuecustomer.views

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.Customer
import eu.tutorials.roadrescuecustomer.viewmodels.LoginViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.RegisterViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    mainActivity: MainActivity,
    registerViewModel: RegisterViewModel,
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
                    text = stringResource(R.string.register_in_to_road_rescue),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = textStyle1
                )
                Spacer(modifier = Modifier.height(12.dp))
                SignUpBox(navHostController, mainActivity, registerViewModel, loginViewModel)
            }
        }
    }
}

@Composable
fun SignUpBox(
    navController: NavHostController,
    mainActivity: MainActivity,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current
    var mAuth: FirebaseAuth? = null
    mAuth = FirebaseAuth.getInstance()
    var otpid by remember {
        mutableStateOf("")
    }    // FirebaseApp.initializeApp(mainActivity)

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
            Spacer(modifier = Modifier.height(8.dp))

            firstName = AuthField("Your First Name", "", false)
            lastName = AuthField("Your Last Name", "", false)
            phoneNumber = AuthField("A Valid Phone Number", "", true)

            AuthFieldBtn {

                if (phoneNumber.isNotEmpty() && phoneNumber.length == 12 && phoneNumber.startsWith("+94")) {
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
                                    loading = false
                                    MainScope().launch {
                                        Toast.makeText(
                                            context,
                                            "Phone number is already registered",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    loading = false
                                    //df
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
                                }
                            }
                        })

                } else {
                    Toast.makeText(context, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
                }
            }
        }
        otp = AuthField("Enter the OTP", "",false)
        AuthCommonButton(
            btnName = "Register",
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            if(firstName.isEmpty() || lastName.isEmpty()) {
                MainScope().launch {
                    Toast.makeText(context, "Fill all the required fields", Toast.LENGTH_SHORT).show()
                }
            }
            else if (otp.isNotEmpty()) {
                loading = true
                val credential =
                    otpid?.let { PhoneAuthProvider.getCredential(it, otp) }
                if (credential != null) {
                    mAuth?.signInWithCredential(credential)
                        ?.addOnCompleteListener(
                            mainActivity
                        ) { task ->
                            loading = false
                            if (task.isSuccessful) {
                                registerViewModel.addUser(
                                    Customer(firstName, lastName, "", phoneNumber),
                                    navController,
                                    context
                                )

//                                navController.navigate("loginscreen") {
//                                    popUpTo("loginscreen") {
//                                        inclusive = true
//                                    }
//                                }
                                MainScope().launch {
                                    Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                MainScope().launch {
                                    Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }else{
                    loading =false
                }
            } else {
                Toast.makeText(context, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Already registered?",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = textStyle2
        )
        AuthCommonButton(
            btnName = "Log in",
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            navController.navigate("loginscreen")
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun AuthField(labelName: String, value: String?, isMobile : Boolean): String {
    var fieldValue by remember { mutableStateOf(TextFieldValue(value ?: "" )) }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fieldValue,
                onValueChange = { fieldValue = it },
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .height(50.dp)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(50))
                    .shadow(6.dp, shape = RoundedCornerShape(50))
                    .background(Color.White)
                    .onFocusChanged {
                        if(isMobile) {
                            if (it.isFocused) {
                                if (fieldValue.text.isEmpty()) {
                                    fieldValue = TextFieldValue("+94", selection = TextRange(3))
                                }
                            } else {
                                // not focused
                            }
                        }
                    },
                textStyle = textStyle2,
                placeholder = {
                    Text(
                        text = labelName,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    return fieldValue.text
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthFieldBtn(onClickButton: () -> Unit) {
    val clicked = remember { mutableStateOf(false) }

    val buttonColor by animateColorAsState(
        targetValue = if (clicked.value) Color(0xFF818EA0) else Color(0xFFC6D4DE),
        animationSpec = tween(durationMillis = 300), label = ""
    )

    val onButtonClick = {
        clicked.value = true
        onClickButton()
        GlobalScope.launch {
            delay(300)
            clicked.value = false
        }
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                onClick = { onButtonClick() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(horizontal = 38.dp, vertical = 8.dp)
                    .width(120.dp),
                colors = CardDefaults.cardColors(containerColor = buttonColor)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "Send OTP",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        style = textStyle2,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthHeader() {
    CenterAlignedTopAppBar(
        title = {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier.size(100.dp),
                tint = Color.Unspecified,
                contentDescription = "Toolbar icon"
            )
        },
        navigationIcon = {
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF253555)
        ),
    )
}

@Composable
fun AuthCommonButton(btnName: String, modifier: Modifier, onClickButton: () -> Unit) {
    val clicked = remember { mutableStateOf(false) }

    val buttonColor by animateColorAsState(
        targetValue = if (clicked.value) Color(0xFF3C4962) else Color(0xFF253555),
        animationSpec = tween(durationMillis = 300), label = ""
    )

    val onButtonClick = {
        clicked.value = true
        onClickButton()
        GlobalScope.launch {
            delay(300)
            clicked.value = false
        }
    }
    Button(
        onClick = { onButtonClick() },
        modifier = modifier.width(200.dp),
        border = BorderStroke(width = 2.dp, color = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(
            text = btnName,
            style = textStyle3, modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}
