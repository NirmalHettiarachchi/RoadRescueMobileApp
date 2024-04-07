package eu.tutorials.roadrescuecustomer.views

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.models.LocationUtils
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.RequestModel
import eu.tutorials.roadrescuecustomer.models.ServiceRequestRepository
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context,
) {
    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            //Welcome text
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome ${AppPreferences(context).getStringPreference("NAME", "")}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle1
            )
            if (!currentStateViewModel.isServiceRequested.value) {
                if (currentStateViewModel.isReqServiceWindowOpened.value) {
                    RequestServiceScreen(
                        onDismiss = {
                            currentStateViewModel.isReqServiceWindowOpened.value = false
                        },
                        currentStateViewModel = currentStateViewModel,
                        serviceRequestViewModel = serviceRequestViewModel,
                        locationUtils = locationUtils,
                        locationViewModel = locationViewModel,
                        context = context
                    )
                } else {
                    NoPendingActivityDashboard(
                        currentStateViewModel = currentStateViewModel,
                        serviceRequestViewModel = serviceRequestViewModel,
                        locationUtils = locationUtils,
                        locationViewModel = locationViewModel,
                        context = context
                    )
                }
            } else {
                PendingActivityDashboard(
                    serviceRequestViewModel = serviceRequestViewModel,
                    currentStateViewModel = currentStateViewModel
                )
            }
            HelpBox()
        }
    }
}

@Composable
fun NoPendingActivityDashboard(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    RequestServiceBox(
        currentStateViewModel,
        serviceRequestViewModel,
        locationUtils,
        locationViewModel,
        context
    )
    CommonIssuesBox(
        currentStateViewModel,
        serviceRequestViewModel,
        locationUtils,
        locationViewModel,
        context
    )
}

@Composable
fun PendingActivityDashboard(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
) {
    val context = LocalContext.current
    var showCostDetailWindow by remember { mutableStateOf(false) }

    var pendingRequest by remember { mutableStateOf("Requesting Service . . .") }

    val loading = remember {
        mutableStateOf(false)
    }
    CircularProgressBar(isDisplayed = loading.value)

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))// Apply shadow to the outer Box
    ) {
        val endTimeMillis =
            System.currentTimeMillis() + 3 * 60 * 1000
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LaunchedEffect(key1 = "periodicCheck") {
                // 3 minutes in milliseconds
                while (System.currentTimeMillis() < endTimeMillis) {
                    serviceRequestViewModel.checkRequest(
                        context,
                        object : ServiceRequestRepository.RequestCallback {
                            override fun success(id: String) {
                                if (id == "1") {

                                } else {
                                    pendingRequest = "Service Requested"
                                }
                            }

                            override fun onError(errorMessage: String) {
                                Toast.makeText(context,"Error Occurred",Toast.LENGTH_SHORT).show()
                            }
                        })

                    delay(15000L) // Wait for 15 seconds before the next call
                }
                if(System.currentTimeMillis() >= endTimeMillis) {
                    serviceRequestViewModel.deleteRequest(
                        context,
                        object : ServiceRequestRepository.RequestCallback {
                            override fun success(id: String) {
                                if (id == "1") {
                                    Toast.makeText(context,"Service request canceled successfully",Toast.LENGTH_SHORT).show()
                                    currentStateViewModel.setCurrentState(false, isReqServiceWindowOpened = false)
                                }
                            }

                            override fun onError(errorMessage: String) {
                                Toast.makeText(context,"Can't able to cancel",Toast.LENGTH_SHORT).show()
                                currentStateViewModel.setCurrentState(false, isReqServiceWindowOpened = false)
                            }
                        }
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "$pendingRequest",
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(16.dp))

            DashboardFieldButton(
                fieldName = "Issue",
                fieldValue = AppPreferences(context).getStringPreference("ISSUE"),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp)
            )
            DashboardFieldButton(
                fieldName = "Vehicle",
                fieldValue = serviceRequestViewModel.vehicleType.value.vehicleType,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp)
            )
            DashboardFieldButton(
                fieldName = "Request ID",
                fieldValue = AppPreferences(context).getStringPreference("REQUEST_ID"),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp)
            )

            Button(
                onClick = { },
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                modifier = Modifier
                    .height(58.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cost: LKR ${
                            AppPreferences(context).getStringPreference("COST")
                        }",
                        style = textStyle2,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.question_fill),
                        contentDescription = "Info",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = AppPreferences(context).getStringPreference(
                    "DESCRIPTION"
                ),
                onValueChange = { },
                modifier = Modifier
                    .height(100.dp)
                    .width(250.dp)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(20))
                    .shadow(2.dp, shape = RoundedCornerShape(20))
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally),
                enabled = false,
                placeholder = {
                    Text(
                        text = "No description provided!",
                        fontSize = 12.sp,
                        color = Color(0xFF253555)
                    )
                },
                textStyle = TextStyle(
                    color = Color(0xFF253555)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            //Cancel btn
            CommonButton(
                btnName = "Cancel Request",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                if (System.currentTimeMillis() < endTimeMillis) {
                    loading.value = true

                    serviceRequestViewModel.deleteRequest(
                        context,
                        object : ServiceRequestRepository.RequestCallback {
                            override fun success(id: String) {
                                if (id == "1") {
                                    Toast.makeText(context,"Service request canceled successfully",Toast.LENGTH_SHORT).show()
                                    currentStateViewModel.setCurrentState(false, isReqServiceWindowOpened = false)
                                    loading.value = false
                                }
                            }

                            override fun onError(errorMessage: String) {
                                Toast.makeText(context,"Can't able to cancel",Toast.LENGTH_SHORT).show()
                                currentStateViewModel.setCurrentState(false, isReqServiceWindowOpened = false)
                                loading.value = false
                            }
                        })
                }else{
                    currentStateViewModel.setCurrentState(false, isReqServiceWindowOpened = false)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if (showCostDetailWindow) {
        MoreInfoWindow(
            "The cost provided is an approximation based on the issue category, vehicle type, and fuel type you have provided. The actual amount may vary.",
            onDismiss = { showCostDetailWindow = false }
        )
    }
}

@Composable
fun RequestServiceBox(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Have you faced a vehicle breakdown?",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Text(
                text = "Just click on the button below...",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(16.dp))

            RequestServiceButton(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                currentStateViewModel.isReqServiceWindowOpened.value = true
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RequestServiceScreen(
    onDismiss: () -> Unit,
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    var description by remember { mutableStateOf("") }

    var showCostDetailWindow by remember { mutableStateOf(false) }
    var showLoadingLocationWindow by remember { mutableStateOf(false) }
    var showVehicleDetailsWindow by remember { mutableStateOf(false) }
    var showIssueDetailsWindow by remember { mutableStateOf(false) }

    showLoadingLocationWindow = locationViewModel.location.value == null

    val loading = remember {
        mutableStateOf(false)
    }

    CircularProgressBar(isDisplayed = loading.value)
    
    if (showLoadingLocationWindow) {
//        MoreInfoWindow(message = "Getting the current location . . . ") {
//
//        }
        CircularProgressBar(isDisplayed = true)
    }

    //Get the current location
    TrackLocation(
        locationUtils = locationUtils,
        locationViewModel = locationViewModel,
        context = context
    )

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))// Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                IconButton(onClick = {
                    onDismiss()
                    locationViewModel.resetLocation()
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        tint = Color.Unspecified
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Tell us more about your issue...",
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(16.dp))

            FillDetailsButton(
                detailButtonName = "Issue Details" +
                        if (serviceRequestViewModel.issue.value.category.isEmpty()) ""
                        else ": ${serviceRequestViewModel.issue.value.category}"
            ) {
                showIssueDetailsWindow = true
            }
            FillDetailsButton(
                detailButtonName = "Vehicle Details" +
                        if (serviceRequestViewModel.vehicleModel.value.vehicleModel.isEmpty()) ""
                        else ": ${
                                    serviceRequestViewModel.vehicleModel.value.vehicleModel +
                                    " (" +
                                    serviceRequestViewModel.fuelType.value.fuelType +
                                    ")"
                        } "
            ) {
                showVehicleDetailsWindow = true
            }

            Button(
                onClick = { showCostDetailWindow = true },
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                modifier = Modifier
                    .width(285.dp)
                    .height(58.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cost: LKR ${serviceRequestViewModel.issue.value.approximatedCost}",
                        style = textStyle2,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.question_fill),
                        contentDescription = "Info",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .height(100.dp)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(20))
                    .shadow(2.dp, shape = RoundedCornerShape(20))
                    .background(Color.White),
                placeholder = {
                    Text(
                        text = "Write a Description (Optional) ... ",
                        fontSize = 12.sp,
                        color = Color(0xFF253555)
                    )
                },
                textStyle = TextStyle(
                    color = Color(0xFF253555)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            RequestServiceButton(modifier = Modifier) {
                if (serviceRequestViewModel.issue.value.id != ""
                    && serviceRequestViewModel.vehicleModel.value.vehicleModel != ""
                ) {
                    loading.value = true
                    AppPreferences(context).setStringPreference(
                        "ISSUE",
                        serviceRequestViewModel.issue.value.category
                    )
                    AppPreferences(context).setStringPreference(
                        "COST",
                        serviceRequestViewModel.issue.value.approximatedCost
                    )
                    AppPreferences(context).setStringPreference(
                        "DESCRIPTION",
                        description
                    )
                    serviceRequestViewModel.setServiceRequest(
                        context,
                        RequestModel(
                            "0",
                            AppPreferences(context).getStringPreference("CUSTOMER_ID", ""),
                            serviceRequestViewModel.issue.value.id,
                            serviceRequestViewModel.indicator1.value,
                            serviceRequestViewModel.indicator2.value,
                            serviceRequestViewModel.indicator3.value,
                            serviceRequestViewModel.indicator4.value,
                            serviceRequestViewModel.indicator5.value,
                            serviceRequestViewModel.indicator6.value,
                            serviceRequestViewModel.vehicleType.value.id,
                            serviceRequestViewModel.vehicleMake.value.id,
                            serviceRequestViewModel.vehicleModel.value.id,
                            serviceRequestViewModel.fuelType.value.id,
                            description,
                            "",
                            locationViewModel.location.value.toString(),
                            "", ""
                        ), object : ServiceRequestRepository.RequestCallback {
                            override fun success(message: String) {
                                Toast.makeText(
                                    context,
                                    "Service requested successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                currentStateViewModel.setCurrentState(
                                    true,
                                    isReqServiceWindowOpened = false
                                )
                                loading.value = false
                            }

                            override fun onError(errorMessage: String) {
                                Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                                loading.value = false
                            }
                        }
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Fill all the required fields to continue",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if (showCostDetailWindow) {
        MoreInfoWindow(
            "The cost provided is an approximation based on the issue category, vehicle type, and fuel type you have provided. The actual amount may vary.",
            onDismiss = { showCostDetailWindow = false }
        )
    }

    if (showVehicleDetailsWindow) {
        VehicleDetailsWindow(serviceRequestViewModel) {
            showVehicleDetailsWindow = false
        }
    }

    if (showIssueDetailsWindow) {
        IssueDetailsWindow(serviceRequestViewModel) {
            showIssueDetailsWindow = false
        }
    }
}

@Composable
fun TrackLocation(
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                //Have access to location
                locationUtils.requestLocationUpdates(locationViewModel = locationViewModel)
            } else {
                val rationalRequired = ActivityCompat
                    .shouldShowRequestPermissionRationale(
                        context as MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) || ActivityCompat
                    .shouldShowRequestPermissionRationale(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )

                if (rationalRequired) {
                    Toast.makeText(
                        context,
                        "Location permission is required for this feature to work.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Location permission is required. Please enable it from the system settings.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    )

    if (locationUtils.hasLocationPermission(context)) {
        locationUtils.requestLocationUpdates(locationViewModel)
    } else {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}

@Composable
fun CommonIssuesBox(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White), shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)) // Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Common vehicle breakdown types",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // First row, first button
                    CommonIssueButton(
                        issueCategory = "Mechanical Issues",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            currentStateViewModel.isReqServiceWindowOpened.value = true
                            serviceRequestViewModel.issue.value.category = "Mechanical Issues"
                        }
                    )
                    // First row, second button
                    CommonIssueButton(
                        issueCategory = "Electrical Issues",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            currentStateViewModel.isReqServiceWindowOpened.value = true
                            serviceRequestViewModel.issue.value.category = "Electrical Issues"
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Second row, first button
                    CommonIssueButton(
                        issueCategory = "Engine Problems",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            currentStateViewModel.isReqServiceWindowOpened.value = true
                            serviceRequestViewModel.issue.value.category = "Engine Problems"
                        }
                    )

                    // Second row, second button
                    CommonIssueButton(
                        issueCategory = "Fuel Issues",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            currentStateViewModel.isReqServiceWindowOpened.value = true
                            serviceRequestViewModel.issue.value.category = "Fuel Issues"
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Third row, first button
                    CommonIssueButton(
                        issueCategory = "Exhaust Issues",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            currentStateViewModel.isReqServiceWindowOpened.value = true
                            serviceRequestViewModel.issue.value.category = "Exhaust Issues"
                        }
                    )

                    // Third row, second button
                    CommonIssueButton(
                        issueCategory = "Cooling Problems",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            currentStateViewModel.isReqServiceWindowOpened.value = true
                            serviceRequestViewModel.issue.value.category = "Cooling Problems"
                        }
                    )
                }
                Button(
                    onClick = {
                        currentStateViewModel.isReqServiceWindowOpened.value = true
                        serviceRequestViewModel.issue.value.category = "Other"
                    },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Other",
                        color = Color(0xFF253555),
                        style = textStyle3.copy(textAlign = TextAlign.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun CommonIssueButton(issueCategory: String, modifier: Modifier, onClickButton: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = { onClickButton() },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 2.dp, color = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
    ) {
        Text(
            text = issueCategory,
            style = textStyle3.copy(textAlign = TextAlign.Center)
        )
    }
}

@Composable
fun RequestServiceButton(modifier: Modifier, onClickButton: () -> Unit) {
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
        modifier = modifier,
        onClick = { onButtonClick() },
        border = BorderStroke(width = 2.dp, color = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.send_fill),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )
            Text(
                text = "Request Service",
                style = textStyle3
            )
        }
    }
}

@Composable
fun DashboardFieldButton(fieldName: String, fieldValue: String, modifier: Modifier) {
    Button(
        onClick = { },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = "$fieldName: $fieldValue",
            color = Color(0xFF253555)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

