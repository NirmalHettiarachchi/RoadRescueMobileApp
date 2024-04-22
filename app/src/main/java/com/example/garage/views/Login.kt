package com.example.garage.views

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.garage.R
import com.example.garage.models.LocationUtils
import com.example.garage.repository.Screen
import com.example.garage.viewModels.LocationViewModel
import com.example.garage.viewModels.LoginShearedViewModel
import com.example.garage.viewModels.MainViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException


@Composable
fun LoginScreen(
    navController: NavHostController,
    loginShearedViewModel: LoginShearedViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel
) {

    val viewModel = viewModel<MainViewModel>()
    val context = LocalContext.current

    Column(
        defaultBackground
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TrackLocation(
            locationUtils = locationUtils,
            locationViewModel = locationViewModel,
            context = context
        )

        Column(
            cardModifier,
            verticalArrangement = Arrangement.Center,
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.log_in_to_road_rescue),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = textStyle1,
                    fontSize = 32.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoginBox(
                    navController = navController,
                    viewModel = viewModel,
                    loginShearedViewModel,
                    locationUtils,
                    locationViewModel
                )
            }
        }
    }
}


@Composable
fun LoginBox(
    navController: NavHostController,
    viewModel: MainViewModel,
    loginShearedViewModel: LoginShearedViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel
) {
    val showDialog = remember { mutableStateOf(false) }
    var showLocationDialog = remember { mutableStateOf(false) }

    var status by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var txtOtp by remember { mutableStateOf("") }
    val context = LocalContext.current
    var id by remember { mutableStateOf("") }


    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))
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
                    coroutineScope.launch {
                        try {
                            if (phoneNumber.isNotEmpty() && phoneNumber.length == 10) {
                                viewModel.checkPhoneNumberIsExists(phoneNumber, "loginSearch") { responseObject ->

                                    if (responseObject != null) {
                                        if (responseObject.status == 200) {
                                            otp= responseObject.message.toString().split(" ").lastOrNull().toString()
                                            id=responseObject.data.toString()
                                            title = "OTP"
                                            message = responseObject.message.toString()
                                            buttonOneName = "null"
                                            buttonTwoName = "null"
                                            showDialog.value = true
                                        } else if(responseObject.status == 204){
                                            title = "Does not exits."
                                            message = responseObject.message.toString()
                                            buttonOneName = "null"
                                            buttonTwoName = "null"
                                            showDialog.value = true
                                        } else if (responseObject.status == 500) {

                                            title = "Failed"
                                            message = responseObject.message.toString()
                                            buttonOneName = "null"
                                            buttonTwoName = "null"
                                            showDialog.value = true
                                        } else {
                                            title = "Phone Number is not exists."
                                            message = responseObject.toString()
                                            buttonOneName = "null"
                                            buttonTwoName = "null"
                                            showDialog.value = true
                                        }
                                    }
                                }
                            } else {
                                title = "Error..!"
                                message =
                                    "Phone number length does not match the required length. Please enter a valid phone number."
                                buttonOneName = "null"
                                buttonTwoName = "null"
                                showDialog.value = true
                            }
                        } catch (e: Exception) {
                            message = e.message.toString()
                            buttonOneName = "null"
                            buttonTwoName = "null"
                            showDialog.value = true
                        } catch (e: SocketTimeoutException) {
                            message = e.message.toString()
                            buttonOneName = "null"
                            buttonTwoName = "null"
                            showDialog.value = true
                        }

                    }
                }
            )
            txtOtp = AuthField("Enter the OTP", "")
            //Edit button
            AuthCommonButton(
                btnName = "Log in",
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {
                if (txtOtp.isEmpty() || phoneNumber.isEmpty()){
                    Toast.makeText(
                        context,
                        "Empty fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    if (txtOtp == otp){

                        val currentLocation =
                            locationViewModel.location.value?.latitude?.let { it1 ->
                                locationViewModel.location.value?.longitude?.let { it2 ->
                                    LatLng(
                                        it1, it2
                                    )
                                }
                            }


                        val part= id.split("-")
                        Log.d("TAG log", "LoginBox: ${part[0]}")
                        Log.d("TAG log", "LoginBox: ${part[1]}")


                        if (currentLocation!=null){
                            if (part[0]=="sp"){
                                loginShearedViewModel.specificLoginId(part[1])
                                navController.navigate(Screen.GarageDashboard.route)
                            }else if (part[0]=="mp"){
                                loginShearedViewModel.specificLoginId(part[1])
//                        navController.navigate(Screen.)
                            }else if (part[0]=="t"){
                                loginShearedViewModel.specificLoginId(part[1])
                                navController.navigate(Screen.TechnicianDashboard.route)
                            }else{
                                Toast.makeText(
                                    context,
                                    "Error matching",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }else{
                            message = "Please turn on device location,with uses RoadRescue service"
                            buttonOneName = "OK"
                            showLocationDialog.value= true
                        }
                    }else{
                        Toast.makeText(
                            context,
                            "OTP is incorrect.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

    if (showDialog.value) {
        sweetAlertDialog(
            title = title,
            message = message,
            buttonOneName = buttonOneName,
            buttonTwoName = buttonTwoName,
            onConfirm = {
                showDialog.value = false
            }
        )
    }

    if (showLocationDialog.value){
        locationAlert(
            message = message,
            buttonOneName = buttonOneName,
            onConfirm = {
                enableLocation(context)
                showLocationDialog.value=false
            }
        )
    }


}


@Composable
fun locationAlert(
    message: String?,
    buttonOneName: String?,
    onConfirm: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = { /* Nothing to do here */ },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "location icon",
                    modifier = deleteIconStyles.size(64.dp),
                    tint = Color(0xB5D32222)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (message != null) {
                    Text(
                        text = message,
                        style = textStyle2,
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        },
        confirmButton = {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    modifier = Modifier.width(80.dp),
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(Color(0xFF253555)),
                    border = BorderStroke(width = 2.dp,color= Color.White)
                ) {

                    if (buttonOneName != null) {
                        Text(text = buttonOneName, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        containerColor = Color(0xC1CCC0C0)
    )
}

private fun enableLocation(context: Context) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        // If location is not enabled, show location settings
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
