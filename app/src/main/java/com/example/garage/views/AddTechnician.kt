package com.example.garage.views


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.garage.models.CheckBoxDetailsModel
import com.example.garage.models.GarageTechnician
import com.example.garage.models.ResponseObject
import com.example.garage.repository.Screen
import com.example.garage.viewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.SocketTimeoutException

@Composable
fun AddTechnician(
    navController: NavController, navyStatus:String
) {
    val viewModel= viewModel<MainViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    var showProgressBar = remember { mutableStateOf(false) }
    var showExpertiseArias by remember { mutableStateOf(false) }

    var status by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }
    var expertiseAriasList by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        val response=loadExpertiseArias(viewModel,coroutineScope)
        if (response != null) {
            if(response?.status==200){

                expertiseAriasList= response.data!!.toString()
                showExpertiseArias=true

            }else if(response.status==400){
                title=response.status.toString()
                message= response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true

            }else if(response.status==404){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true

            }else if(response.status==500){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true
            }else if(response.status==508){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="null"
                buttonTwoName="null"
                showDialog.value=true
            }else{
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true
            }
        }else{
            status=401
            message="Cannot call the sever"
            buttonOneName="Ok"
            buttonTwoName="null"
            showDialog.value=true
            Log.d("response null","null")
        }
    }


    Column(
        modifier = defaultBackground,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {



        Header(menuClicked = {})

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Register Technician",
            style = textStyle4,
            modifier = Modifier,
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = cardDefaultModifier
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
            border = BorderStroke(width = 2.dp, Color.White),
        ) {
            var textFirstName by remember { mutableStateOf("") }
            var textLastName by remember { mutableStateOf("") }
            var textContactNumber by remember { mutableStateOf("") }
            var selectedServices by remember { mutableStateOf(emptyList<String>()) }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    Spacer(modifier = Modifier.height(16.dp))
                    textFirstName=CommonTextField(textFirstName, true, "First Name",Modifier.weight(1f),false,KeyboardType.Text)
                    Spacer(modifier = Modifier.height(16.dp))

                    textLastName=CommonTextField(textLastName, true, "Last Name",Modifier.weight(1f),false,KeyboardType.Text)
                    Spacer(modifier = Modifier.height(16.dp))

                    textContactNumber=CommonTextField(textContactNumber, true, "Contact Number",Modifier.weight(1f),true,KeyboardType.Number)
                    Spacer(modifier = Modifier.height(16.dp))

                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Expertise Areas",
                    style = textStyle4,
                    modifier = Modifier.padding(0.dp,0.dp,100.dp,0.dp),
                    fontSize = 26.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ){
                    val isCheckedBreakSystem by remember { mutableStateOf(false) }
                    val checkboxColor = if(isCheckedBreakSystem) Color(0xFF253555) else Color.White

                    val servicesList= ArrayList<CheckBoxDetailsModel>()

                    if (showExpertiseArias) {
                        val jsonArray = JSONArray(expertiseAriasList)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val techExpertiseId = jsonObject.getString("expertiseId")
                            val techExpertise = jsonObject.getString("expertise")
                            servicesList.add(CheckBoxDetailsModel(techExpertiseId,techExpertise, false))
                        }
                    }


                    servicesList.forEach{service->

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {

                            Spacer(modifier = Modifier.width(8.dp))

                            Checkbox(
                                checked = selectedServices.contains(service.getCheckBoxName()),
                                onCheckedChange = { isChecked ->
                                    selectedServices = if (isChecked) {
                                        selectedServices + service.getCheckBoxName()+"-"+service.getCheckBoxId()
                                    } else {
                                        selectedServices - service.getCheckBoxName()+"-"+service.getCheckBoxId()
                                    }
                                },
                                modifier = Modifier
                                    .background(color = checkboxColor)
                                    .size(20.dp)
                                    .padding(4.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = service.getCheckBoxName(),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily
                            )

                        }

                    }

                }

                 Spacer(modifier = Modifier.height(16.dp))

                CommonButton(
                    btnName = "Register",
                    modifier = Modifier,
                    onClickButton = {


                        coroutineScope.launch {
                            try {
//                                showProgressBar.value=true
                                viewModel.addTechnician(GarageTechnician(textFirstName,textLastName,textContactNumber,selectedServices,1)) { responseObject ->


                                    if (responseObject != null) {


                                        if(responseObject.status==201){
                                            title="Success"
                                            message= responseObject.message.toString()
                                            buttonOneName="null"
                                            buttonTwoName="null"
//                                            showProgressBar.value=false
                                            showDialog.value=true

                                            textFirstName = textFirstName.trim()
                                            textLastName = textLastName.trim()
                                            textContactNumber = textContactNumber.trim()

                                        }else if (responseObject.status==500){

                                            title="Failed"
                                            message= responseObject.message.toString()
                                            buttonOneName="null"
                                            buttonTwoName="null"
//                                            showProgressBar.value=false
                                            showDialog.value=true
                                        }else{
                                            title="Failed"
                                            message= responseObject.toString()
                                            buttonOneName= "null"
                                            buttonTwoName="null"
//                                            showProgressBar.value=false
                                            showDialog.value=true
                                        }
                                    }
                                }
                            }catch (e:SocketTimeoutException){
                                // Handle timeout exception
//                                showProgressBar.value=false
                                showDialog.value=true
                                message= e.message.toString()
                                buttonOneName= "null"
                                buttonTwoName="null"
                                Log.e("NetworkRequest","SocketTimeoutException: ${e.message}")
                            }catch (e:Exception){
                                // Handle other exceptions
//                                showProgressBar.value=false
                                showDialog.value=true
                                message= e.message.toString()
                                buttonOneName= "Ok"
                                buttonTwoName="null"
                                Log.e("NetworkRequest", "Exception: ${e.message}")
                            }
                        }
                    })

                // load progress bar
                if(showProgressBar.value){
                    circularIndicatorProgressBar()
                }

                // load response message
                if (showDialog.value){
                    sweetAlertDialog(
                        title = title,
                        message = message,
                        buttonOneName = buttonOneName,
                        buttonTwoName = buttonTwoName,
                        onConfirm = {
                            showDialog.value=false
                            navController.navigate(route = Screen.TechnicianList.route)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(26.dp))
        Footer(navController,navyStatus)
    }
}

suspend fun loadExpertiseArias(viewModel: MainViewModel, coroutineScope: CoroutineScope): ResponseObject? {
    var response: ResponseObject? =null

    try {
        viewModel.getExpertiseArias("","expertise"){responseObject ->
            if (responseObject!=null){
                response=responseObject
            }else{
                response= ResponseObject(400,"response is null",null)
            }
        }
    }catch (e:SocketTimeoutException){
        // handle
        response=ResponseObject(508,"Request time out.\n Please try again.",e.localizedMessage)
    }catch (e:Exception){
        response=ResponseObject(404 ,"Exception error.",e.localizedMessage)
    }

    return response
}

