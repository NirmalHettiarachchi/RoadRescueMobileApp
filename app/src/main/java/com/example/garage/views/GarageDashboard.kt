package com.example.garage.views

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.garage.models.Garage
import com.example.garage.models.ResponseObject
import com.example.garage.models.ServicesRequestModel
import com.example.garage.repository.GarageCommonDetails
import com.example.garage.viewModels.GarageSharedViewModel
import com.example.garage.viewModels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.SocketTimeoutException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GarageDashboard(
    navController: NavController,
    navStatus: String,
    garageSharedViewModel: GarageSharedViewModel,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel = viewModel<MainViewModel>()

    var showLoadGarageDetails by remember { mutableStateOf(false) }
    var showServiceRequests by remember { mutableStateOf(false) }
    var showMessageDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }
    var garage by remember { mutableStateOf("") }
    var requestServices by remember { mutableStateOf("") }
    var garageDetailsBackend = Garage()


    LaunchedEffect(Unit) {
        val response = loadGarageDetails(viewModel)
        if (response != null) {
            if (response.status == 200) {

                garage = response.data!!.toString()
//                showProgressBar=false
                showLoadGarageDetails = true

            } else if (response.status == 400) {
                title = response.status.toString()
                message = response.message.toString()
                buttonOneName = "Ok"
                buttonTwoName = "null"
                showMessageDialog = true

            } else if (response.status == 404) {
                title = response.status.toString()
                message = response.message.toString()
                buttonOneName = "Ok"
                buttonTwoName = "null"
                showMessageDialog = true

            } else if (response.status == 500) {
                title = response.status.toString()
                message = response.message.toString()
                buttonOneName = "Ok"
                buttonTwoName = "null"
                showMessageDialog = true
            } else if (response.status == 508) {
                title = response.status.toString()
                message = response.message.toString()
                buttonOneName = "null"
                buttonTwoName = "null"
                showMessageDialog = true
            } else {
                title = response.status.toString()
                message = response.message.toString()
                buttonOneName = "Ok"
                buttonTwoName = "null"
                showMessageDialog = true
            }
        } else {
            title = "401"
            message = "Cannot call the sever"
            buttonOneName = "Ok"
            buttonTwoName = "null"
            showMessageDialog = true
            Log.d("response null", "null")
        }



            // fetch services requests every 1 minutes
        while (true) {
            requestServices=""
            val serviceResponse=fetchServiceRequests(viewModel)

            if (serviceResponse != null) {
                if (serviceResponse.status == 200) {
                    requestServices=serviceResponse.data!!.toString()
                    showServiceRequests=true
                }else if (serviceResponse.status == 400) {
                    title = serviceResponse.status.toString()
                    message = serviceResponse.message.toString()
                    buttonOneName = "Ok"
                    buttonTwoName = "null"
                    showMessageDialog = true

                } else if (serviceResponse.status == 404) {
                    title = serviceResponse.status.toString()
                    message = serviceResponse.message.toString()
                    buttonOneName = "Ok"
                    buttonTwoName = "null"
                    showMessageDialog = true

                } else if (serviceResponse.status == 500) {
                    title = serviceResponse.status.toString()
                    message = serviceResponse.message.toString()
                    buttonOneName = "Ok"
                    buttonTwoName = "null"
                    showMessageDialog = true
                } else if (serviceResponse.status == 508) {
                    title = serviceResponse.status.toString()
                    message = serviceResponse.message.toString()
                    buttonOneName = "null"
                    buttonTwoName = "null"
                    showMessageDialog = true
                }
            }else{
                title = "401"
                message = "Cannot call the sever"
                buttonOneName = "Ok"
                buttonTwoName = "null"
                showMessageDialog = true
            }
            delay(1000 * 60)
        }



    }


    if (showLoadGarageDetails) {
        try {
            val jsonObject = JSONObject(garage)

            val garageName = jsonObject.getString("garageName")
            val ownerName = jsonObject.getString("OwnerName")
            val garageContactNumber = jsonObject.getString("contactNumber")
            val garageStatus = jsonObject.getString("garageStatus")
            val garageEmail = jsonObject.getString("email")
            val garageRating = jsonObject.getString("garageRating").toFloat()
            val garageType = jsonObject.getString("garageType")
            val garageProfileImageRef = jsonObject.getString("imageRef")

            garageDetailsBackend.setGarageName(garageName)
            garageDetailsBackend.setOwnerName(ownerName)
            garageDetailsBackend.setGarageContactNumber(garageContactNumber)
            garageDetailsBackend.setGarageStatus(garageStatus)
            garageDetailsBackend.setGarageEmail(garageEmail)
            garageDetailsBackend.setGarageRating(garageRating)
            garageDetailsBackend.setGarageType(garageType)
            garageDetailsBackend.setGarageProfilePicRef(garageProfileImageRef)


            // Sheared garage common details using Parcelize
            val garageCommonDetails = GarageCommonDetails(
                "1",
                garageDetailsBackend.getGarageName(),
                garageDetailsBackend.getGarageContactNumber(),
                garageDetailsBackend.getGarageStatus(),
                garageDetailsBackend.getGarageEmail(),
                garageDetailsBackend.getGarageRating().toString(),
                garageDetailsBackend.getGarageType(),
                garageDetailsBackend.getOwnerName(),
                garageDetailsBackend.getGarageProfilePicRef()
            )
            garageSharedViewModel.garageCommonDetails(garageCommonDetails)


        } catch (e: JSONException) {
            e.localizedMessage?.let { it1 -> Log.d("json error", it1) }
        }

    }




    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    SidebarContent() {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                Header {
                    scope.launch { drawerState.open() }
                }
            },
            bottomBar = {
                Footer(navController, navStatus)
            }
        ) {
            Column(
                modifier = defaultBackground.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Welcome, ${garageDetailsBackend.getGarageName()}",
                    color = Color(0xFF253555),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    style = textStyle4
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.84f)
                        .fillMaxHeight(0.95f)
                        .verticalScroll(state = rememberScrollState()),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
                    border = BorderStroke(width = 2.dp, Color.White),

                    ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    // Load are service requests
                    // customer request load karanna one
                    if(showServiceRequests){
                        if (requestServices.isNotEmpty()) {
                            val jsonArray=JSONArray(requestServices)
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val issue = jsonObject.getString("issue")
                                val customerContactNumber = jsonObject.getString("customerContactNumber")
                                val approx_cost = jsonObject.getDouble("approx_cost")
                                val requestTime = jsonObject.getString("requestTimeStamp")
                                val description = jsonObject.getString("description")
                                val indicatorLightStatus = jsonObject.getString("indicatorLightStatus")
                                val serviceRequestId = jsonObject.getInt("serviceRequestId")


                                var serviceRequest=ServicesRequestModel(serviceRequestId,customerContactNumber,requestTime,issue,description,approx_cost,indicatorLightStatus)

                                ServiceRequest(
                                    serviceRequest,
                                    Modifier.align(Alignment.CenterHorizontally),
                                    viewModel
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

            }

            if (showMessageDialog) {
                sweetAlertDialog(
                    title = title,
                    message = message,
                    buttonOneName = buttonOneName,
                    buttonTwoName = buttonTwoName,
                    onConfirm = {
                        showMessageDialog = false
                    }
                )
            }
        }
    }
}

suspend fun fetchServiceRequests(viewModel: MainViewModel): ResponseObject?{
    var response: ResponseObject? = null

    try {
        viewModel.getGarageServiceRequest("1", "getServices") { responseObject ->
            if (responseObject != null) {
                response = responseObject
            } else {
                response = ResponseObject(400, "response is null", null)
            }
        }
    }catch (e: SocketTimeoutException) {
        response = ResponseObject(508, "Request time out.\n Please try again.", e.localizedMessage)
    } catch (e: Exception) {
        response = ResponseObject(404, "Exception error.", e.localizedMessage)
    }
    return response
}

suspend fun loadGarageDetails(viewModel: MainViewModel): ResponseObject? {
    var response: ResponseObject? = null

    try {
        viewModel.getGarageDetails("1", "search") { responseObject ->
            if (responseObject != null) {
                response = responseObject
            } else {
                response = ResponseObject(400, "response is null", null)
            }
        }
    } catch (e: SocketTimeoutException) {
        response = ResponseObject(508, "Request time out.\n Please try again.", e.localizedMessage)
    } catch (e: Exception) {
        response = ResponseObject(404, "Exception error.", e.localizedMessage)
    }
    return response
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ServiceRequest(serviceRequest: ServicesRequestModel, modifier: Modifier,viewModel: MainViewModel) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val phoneNumber = serviceRequest.getCustomerContactNumber()
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }
    var showMessageDialog by remember { mutableStateOf(false) }

    var techniciansList = emptyList<String>()

    
    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.35f)
            .shadow(elevation = 8.dp, shape = RectangleShape),
        colors = CardDefaults.cardColors(containerColor = Color.White),

        ) {

        var showDialog by remember { mutableStateOf(false) }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You have new service request ${serviceRequest.getTime()}",
            color = Color(0xFF253555),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Divider(color = Color(0xFF253555), thickness = 2.dp)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Issue", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = serviceRequest.getIssue(),
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Indicator light state", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = serviceRequest.getIndicatorState(),
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Description", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = if (serviceRequest.getDescription().isEmpty()) "No Description" else serviceRequest.getDescription(),
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Service Fees(approx..)", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = "LKR ${serviceRequest.getServiceFee()}0",
                color = Color.Black,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

                context.startActivity(intent)

            }) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Contact icon",
                    tint = Color.Black
                )
            }

            CommonButton(
                btnName = "Accept",
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {

                coroutineScope.launch {
                    var technicianResponse: ResponseObject? = null
                    when (serviceRequest.getIssue()) {
                        "Mechanical Issues" -> {
                            technicianResponse = loadTechniciansGroupByIssue(viewModel, "Other")
                        }

                        "Electrical Issues" -> {
                            technicianResponse = loadTechniciansGroupByIssue(
                                viewModel,
                                "Electrical System Troubleshooting"
                            )
                        }

                        "Engine Problems" -> {
                            technicianResponse =
                                loadTechniciansGroupByIssue(viewModel, "Engine Maintenance")
                        }

                        "Fuel Issues" -> {
                            technicianResponse =
                                loadTechniciansGroupByIssue(viewModel, "Oil System Maintenance")
                        }

                        "Exhaust Issues" -> {
                            technicianResponse =
                                loadTechniciansGroupByIssue(viewModel, "Engine Maintenance")
                        }

                        "Cooling Problems" -> {
                            technicianResponse = loadTechniciansGroupByIssue(viewModel, "HVAC")
                        }

                        "Other" -> {
                            technicianResponse = loadTechniciansGroupByIssue(viewModel, "Other")
                        }
                    }

                    if (technicianResponse != null) {
                        if (technicianResponse.status == 200) {

                            val filterTechnicians = technicianResponse.data!!.toString()

                            val jsonArray = JSONArray(filterTechnicians)

                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val techId = jsonObject.getString("techId")
                                val techFName = jsonObject.getString("f_name")
                                val techLName = jsonObject.getString("l_name")

                                techniciansList += "$techId-$techFName $techLName"
                            }

                        } else if (technicianResponse.status == 400) {
                            title = technicianResponse.status.toString()
                            message = technicianResponse.message.toString()
                            buttonOneName = "Ok"
                            buttonTwoName = "null"
                            showMessageDialog = true

                        } else if (technicianResponse.status == 404) {
                            title = technicianResponse.status.toString()
                            message = technicianResponse.message.toString()
                            buttonOneName = "Ok"
                            buttonTwoName = "null"
                            showMessageDialog = true

                        } else if (technicianResponse.status == 500) {
                            title = technicianResponse.status.toString()
                            message = technicianResponse.message.toString()
                            buttonOneName = "Ok"
                            buttonTwoName = "null"
                            showMessageDialog = true
                        } else if (technicianResponse.status == 508) {
                            title = technicianResponse.status.toString()
                            message = technicianResponse.message.toString()
                            buttonOneName = "null"
                            buttonTwoName = "null"
                            showMessageDialog = true
                        } else {
                            title = technicianResponse.status.toString()
                            message = technicianResponse.message.toString()
                            buttonOneName = "Ok"
                            buttonTwoName = "null"
                            showMessageDialog = true
                        }
                    } else {
                        title = "401"
                        message = "Cannot call the sever"
                        buttonOneName = "Ok"
                        buttonTwoName = "null"
                        showMessageDialog = true
                    }
                }
                showDialog = true
            }

            if (showDialog) {
                Dialog(
                    onDismissRequest = { },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .fillMaxHeight(0.4f)
                                .background(
                                    Color(0xFFACB3C0),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = {
                                    techniciansList= emptyList()
                                    showDialog = false
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close icon",
                                        modifier = closerButtonStyles,
                                        tint = Color.White
                                    )
                                }
                            }


                            Text(
                                text = "Assign a Technician ",
                                style = textStyle4,
                                modifier = Modifier,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Dropdown load


                            val option= CommonDropdown(
                                optionList = techniciansList,
                                defaultSelection = "Technician "
                            )


                            Spacer(modifier = Modifier.height(64.dp))

                            // accept button load

                            Log.d("dropDownValue", "$option")

                            CommonButton(
                                btnName = "Assign",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClickButton = {
                                    val serviceProviderId="1"
                                    val parts = option.toString().split("-")
                                    coroutineScope.launch {
                                        assignTechnicianForService(serviceRequest.getServiceRequestId(),serviceProviderId,parts[0])
                                    }

                                }
                            )

                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (showMessageDialog) {
        sweetAlertDialog(
            title = title,
            message = message,
            buttonOneName = buttonOneName,
            buttonTwoName = buttonTwoName,
            onConfirm = {
                showMessageDialog = false
            }
        )
    }
}



suspend fun assignTechnicianForService(
    serviceRequestId: Int, serviceProviderId: String,
    technicianId: String
):ResponseObject? {
    var response: ResponseObject? = null

    try {
       /* viewModel.getTechnicians("$issueCategory-1", "filterTechByIssue") { responseObject ->
            if (responseObject != null) {
                response = responseObject
            } else {
                response = ResponseObject(400, "response is null", null)
            }
        }*/
    } catch (e: SocketTimeoutException) {
        // handle
        response = ResponseObject(508, "Request time out.\n Please try again.", e.localizedMessage)
    } catch (e: Exception) {
        response = ResponseObject(404, "Exception error.", e.localizedMessage)
    }

    return response
}

suspend fun loadTechniciansGroupByIssue(viewModel: MainViewModel,issueCategory:String):ResponseObject? {
    var response: ResponseObject? = null

    try {
        viewModel.getTechnicians("$issueCategory-1", "filterTechByIssue") { responseObject ->
            if (responseObject != null) {
                response = responseObject
            } else {
                response = ResponseObject(400, "response is null", null)
            }
        }
    } catch (e: SocketTimeoutException) {
        // handle
        response = ResponseObject(508, "Request time out.\n Please try again.", e.localizedMessage)
    } catch (e: Exception) {
        response = ResponseObject(404, "Exception error.", e.localizedMessage)
    }

    return response

}



