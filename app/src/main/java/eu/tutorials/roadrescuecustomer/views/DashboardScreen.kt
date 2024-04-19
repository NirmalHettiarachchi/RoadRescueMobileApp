package eu.tutorials.roadrescuecustomer.views

import android.Manifest
import android.content.Context
import android.util.Log
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.rememberNavController
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.models.LocationUtils
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.ServiceRequest
import eu.tutorials.roadrescuecustomer.models.ServiceRequestRepository
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LocationViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@Composable
fun DashboardScreen(
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context,
    navController: NavHostController
) {

    var loading by remember {
        mutableStateOf(currentStateViewModel.loading)
    }
    CircularProgressBar(isDisplayed = loading.value)

    LaunchedEffect(key1 = true) {
        currentStateViewModel.fetchLatestRequest(
            AppPreferences(context).getStringPreference(
                "CUSTOMER_ID",
                ""
            ),
            showLoading = true
        )
    }

    var showPending by remember {
        mutableStateOf(false)
    }

    var isChangeAutomatic by remember {
        mutableStateOf(false)
    }

    var waitingTime by remember {
        mutableLongStateOf(0L)
    }

    val request = currentStateViewModel.latestRequests.collectAsState().value.firstOrNull()

    if (currentStateViewModel.latestRequests.collectAsState().value.isNotEmpty()) {
        val first = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        first.timeZone = TimeZone.getTimeZone("UTC") // Or whatever IST is supposed to be
        val dd =
            first.parse(currentStateViewModel.latestRequests.collectAsState().value.first().date)

        val formatterUTC: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        formatterUTC.timeZone = TimeZone.getTimeZone("UTC") // UTC timezone
        val d = Date(System.currentTimeMillis())
        val ddd = Date()

        //get time in milliseconds
        val diff: Long = ddd.time - dd.time

        val seconds = diff / 1000

        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        Log.d(
            "TAG",
            "DashboardScreen: Date Fetched From UTC ${currentStateViewModel.latestRequests.collectAsState().value.first().date}"
        )
//        Log.d("TAG", "DashboardScreen: ddd.time.... ${ddd.time}")
//        Log.d("TAG", "DashboardScreen: ddd.... $diff")
//        Log.d("TAG", "DashboardScreen: True.... $minutes")
        Log.d("TAG", "DashboardScreen: Date1 ==== $dd    Date2 ====$ddd")


        val date1 = System.currentTimeMillis()
        Thread.sleep(1000)
        val date2 = System.currentTimeMillis()

        val duration = abs(ddd.time - dd.time)
        val days1 = TimeUnit.MILLISECONDS.toDays(duration)
        val hours1 = TimeUnit.MILLISECONDS.toHours(duration)
        val minutes1 = TimeUnit.MILLISECONDS.toMinutes(duration)

        Log.d("TAG", "DashboardScreen: Duration Minutes....... $minutes1")
        waitingTime = (3 - minutes1) * 60 * 1000

        val seconds1 = TimeUnit.MILLISECONDS.toSeconds(duration)

        if(isChangeAutomatic){
            currentStateViewModel.setCurrentState(
                false,
                isReqServiceWindowOpened = false
            )
        }

        if (minutes <= 3 && currentStateViewModel.latestRequests.collectAsState().value.first().status.toInt() == 1) {
            showPending = true
        } else {
            showPending = false
        }
    } else {
        showPending = false
    }

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
            if (!currentStateViewModel.isServiceRequested.value && !showPending) {
                Log.d("TAG", "DashboardScreen: Step 1")

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
                    if (request?.status?.toInt() == 2) {
                        LaunchedEffect(key1 = true) {
                            while (true) {
                                currentStateViewModel.fetchLatestRequest(
                                    AppPreferences(context).getStringPreference(
                                        "CUSTOMER_ID",
                                        ""
                                    ),
                                    showLoading = false
                                )
                                isChangeAutomatic = true
                                delay(10000)
                            }
                        }
                        AssignedActivityDashboard(
                            request = request,
                            serviceRequestViewModel = serviceRequestViewModel,
                            currentStateViewModel = currentStateViewModel,
                            navController = navController
                        )
                    } else if (request?.status?.toInt() == 3) {
                        ServiceProvidedDashboard(
                            request = request,
                            serviceRequestViewModel = serviceRequestViewModel,
                            currentStateViewModel = currentStateViewModel,
                            navController = navController
                        )
                    } else if (request?.status?.toInt() == 4) {
                        RateScreen(
                            request = request,
                            serviceRequestViewModel = serviceRequestViewModel,
                            onRate = {
                                currentStateViewModel.setCurrentState(
                                    false,
                                    isReqServiceWindowOpened = false
                                )
                                currentStateViewModel.clearRecentRequest()
                                showPending = false
                            })
                    } else {
                        isChangeAutomatic = false
                        NoPendingActivityDashboard(
                            currentStateViewModel = currentStateViewModel,
                            serviceRequestViewModel = serviceRequestViewModel,
                            locationUtils = locationUtils,
                            locationViewModel = locationViewModel,
                            context = context
                        )
                    }
                }
            } else {

                if (request?.status?.toInt() == 1) {
                    LaunchedEffect(key1 = true) {
                        while (true) {
                            currentStateViewModel.fetchLatestRequest(
                                AppPreferences(context).getStringPreference(
                                    "CUSTOMER_ID",
                                    ""
                                ),
                                showLoading = false
                            )
                            isChangeAutomatic = true
                            delay(10000)
                        }
                    }
                    PendingActivityDashboard(
                        waitingTime = waitingTime,
                        request = request,
                        serviceRequestViewModel = serviceRequestViewModel,
                        currentStateViewModel = currentStateViewModel
                    ) {
                        currentStateViewModel.setCurrentState(
                            false,
                            isReqServiceWindowOpened = false
                        )
                        currentStateViewModel.clearRecentRequest()
                        showPending = false
                    }
                }
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
    waitingTime: Long,
    request: ServiceRequest,
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    var showCostDetailWindow by remember { mutableStateOf(false) }

    var pendingRequest by remember { mutableStateOf("Requesting Service . . .") }

    val loading by serviceRequestViewModel.loading

    LaunchedEffect(key1 = Unit) {
        serviceRequestViewModel.deleteLoading.collect { close ->
            if (close) {
                onClick()
            }
        }
    }


    if (currentStateViewModel.latestRequests.collectAsState().value.isNotEmpty()) {
        val first = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        first.timeZone = TimeZone.getTimeZone("UTC") // Or whatever IST is supposed to be
        val dd =
            first.parse(currentStateViewModel.latestRequests.collectAsState().value.first().date)

        val formatterUTC: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        formatterUTC.timeZone = TimeZone.getTimeZone("UTC") // UTC timezone
        val ddd = Date()

        val duration = abs(ddd.time - dd.time)
        val minutes1 = TimeUnit.MILLISECONDS.toMinutes(duration)

        Log.d("TAG", "PendingActivityDashboard: Current Minutes Diff $minutes1")

        val wt = (3 - minutes1) * 60 * 1000
        Log.d("TAG", "PendingActivityDashboard: Current Minutes === ${3 - minutes1}")
        Log.d("TAG", "PendingActivityDashboard: Current Pending Time === ${wt}")

        if (minutes1 <= 3) {
            Timer(waitingTime = wt) {
                serviceRequestViewModel.deleteRequest(
                    context,
                    request.id.toInt()
                )
            }
        }
    }


    CircularProgressBar(isDisplayed = loading)

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
//            LaunchedEffect(key1 = "periodicCheck") {
//                // 3 minutes in milliseconds
//                while (System.currentTimeMillis() < endTimeMillis) {
//                    serviceRequestViewModel.checkRequest(
//                        context,
//                        object : ServiceRequestRepository.RequestCallback {
//                            override fun success(id: String) {
//                                if (id == "1") {
//
//                                } else {
//                                    pendingRequest = "Service Requested"
//                                }
//                            }
//
//                            override fun onError(errorMessage: String) {
//                                Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
//                            }
//                        })
//
//                    delay(15000L) // Wait for 15 seconds before the next call
//                }
//                if (System.currentTimeMillis() >= endTimeMillis) {
//                    serviceRequestViewModel.deleteRequest(
//                        context
//                    )
//                }
//            }

            Text(
                text = "$pendingRequest",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(20.dp))

            //todo
            AnimatedCircle(isDisplayed = true)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { showCostDetailWindow = true },
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
                            request.approxCost
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

            var showRequestDetailsWindow by remember {
                mutableStateOf(false)
            }

            CommonButton(
                btnName = "Show Details",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                showRequestDetailsWindow = true
            }

            if (showRequestDetailsWindow) {
                RequestDetails(
                    request = request,
                    serviceRequestViewModel = serviceRequestViewModel,
                    context = context
                ) {
                    showRequestDetailsWindow = false
                }
            }

            //Cancel btn
            CommonButton(
                btnName = "Cancel Request",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                if (System.currentTimeMillis() < endTimeMillis) {
                    serviceRequestViewModel.deleteRequest(
                        context,
                        request.id.toInt()
                    )
                } else {
                    //todo
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if (showCostDetailWindow) {
        MoreInfoWindow(
            "The cost provided is an approximation based on the issue category you have provided." +
                    " The actual amount may vary.",
            onDismiss = { showCostDetailWindow = false }
        )
    }
}

@Composable
private fun Timer(
    waitingTime: Long,
    onFinish: () -> Unit
) = LaunchedEffect(waitingTime) {
    delay(waitingTime)
    onFinish()
}


@Composable
fun AssignedActivityDashboard(
    request: ServiceRequest,
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    var showCostDetailWindow by remember { mutableStateOf(false) }

    val loading by serviceRequestViewModel.loading


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
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${request.serviceProviderName} is Assigned . . .",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(20.dp))

            //todo
            AnimatedCircle(isDisplayed = true)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { showCostDetailWindow = true },
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
                        text = "Cost: LKR ${request.approxCost}",
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

            var showRequestDetailsWindow by remember {
                mutableStateOf(false)
            }

            CommonButton(
                btnName = "Show Details",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                showRequestDetailsWindow = true
            }

            if (showRequestDetailsWindow) {
                RequestDetails(
                    request = request,
                    serviceRequestViewModel = serviceRequestViewModel,
                    context = context
                ) {
                    showRequestDetailsWindow = false
                }
            }

            CommonButton(
                btnName = "Track",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                navController.navigate("tracklocationscreen")
            }
//            Spacer(modifier = Modifier.height(16.dp))

            CommonButton(
                btnName = "Any Issue",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                navController.navigate("helpscreen")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if (showCostDetailWindow) {
        MoreInfoWindow(
            "The cost provided is an approximation based on the issue category you have provided." +
                    " The actual amount may vary.",
            onDismiss = { showCostDetailWindow = false }
        )
    }
}


@Composable
fun ServiceProvidedDashboard(
    request: ServiceRequest,
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    var showCostDetailWindow by remember { mutableStateOf(false) }

    val loading by serviceRequestViewModel.loading


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
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Service Provided . . .",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                style = textStyle2,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            //todo
            AnimatedCircle(isDisplayed = true)

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Please make the payment of \nLKR ${request.reqAmount}",
                style = textStyle2,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            var showRequestDetailsWindow by remember {
                mutableStateOf(false)
            }

            var showPaymentDialog by remember {
                mutableStateOf(false)
            }

            CommonButton(
                btnName = "Make Payment",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                showPaymentDialog = true
            }

            CommonButton(
                btnName = "Show Details",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                showRequestDetailsWindow = true
            }

            if (showRequestDetailsWindow) {
                RequestDetails(
                    request = request,
                    serviceRequestViewModel = serviceRequestViewModel,
                    context = context
                ) {
                    showRequestDetailsWindow = false
                }
            }

            if (showPaymentDialog) {
                PaymentMethodDialog(serviceRequestViewModel) {
                    showPaymentDialog = false
                }
            }

            CommonButton(
                btnName = "Any Issue",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                navController.navigate("helpscreen")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if (showCostDetailWindow) {
        MoreInfoWindow(
            "The cost provided is an approximation based on the issue category you have provided." +
                    " The actual amount may vary.",
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
                detailButtonName =
                if (serviceRequestViewModel.issue.value.category.isEmpty()) "Issue Details (required)"
                else "Issue: ${serviceRequestViewModel.issue.value.category}"
            ) {
                showIssueDetailsWindow = true
            }
            FillDetailsButton(
                detailButtonName =
                if (serviceRequestViewModel.vehicleModel.value.vehicleModel.isEmpty()) "Vehicle Details (required)"
                else "Vehicle: ${
                    serviceRequestViewModel.vehicleModel.value.vehicleModel
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
                        style = textStyle5,
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
                    .width(265.dp)
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
                    AppPreferences(context).setStringPreference(
                        "VEHICLE_MODEL",
                        serviceRequestViewModel.vehicleModel.value.vehicleModel
                    )
                    serviceRequestViewModel.setServiceRequest(
                        context,
                        ServiceRequest(
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
                            "",
                            "",
                            approxCost = serviceRequestViewModel.issue.value.approximatedCost
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
                                currentStateViewModel.fetchLatestRequest(
                                    AppPreferences(context).getStringPreference(
                                        "CUSTOMER_ID",
                                        ""
                                    )
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
            "The cost provided is an approximation based on the issue category you have provided. The actual amount may vary.",
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
                        "Location permission is required for this feature to work",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Location permission is required. Please enable it from the system settings",
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
                serviceRequestViewModel.fetchIssues()
                val issues by serviceRequestViewModel.issues
                issues.forEach { issue ->
                    CommonIssueButton(issueCategory = issue.category) {
                        currentStateViewModel.isReqServiceWindowOpened.value = true
                        serviceRequestViewModel.issue.value.category = issue.category
                        serviceRequestViewModel.issue.value.approximatedCost =
                            issue.approximatedCost
                        serviceRequestViewModel.issue.value.id = issue.id
                    }
                }
            }
        }
    }
}

@Composable
fun CommonIssueButton(issueCategory: String, onClickButton: () -> Unit) {
    Button(
        onClick = {
            onClickButton()
        },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = issueCategory,
            color = Color(0xFF253555),
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

@Composable
fun RequestDetails(
    request: ServiceRequest,
    serviceRequestViewModel: ServiceRequestViewModel,
    context: Context,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .border(2.dp, Color.White, shape = RoundedCornerShape(10))
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .verticalScroll(rememberScrollState()),
        tonalElevation = 16.dp,
        containerColor = Color(0xFFDCE4EC),
        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Service Request Details",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))

                DashboardFieldButton(
                    fieldName = "Issue",
                    fieldValue = request.issueCategoryName,
                    modifier = Modifier
                        .width(250.dp)
                )
                DashboardFieldButton(
                    fieldName = "Vehicle",
                    fieldValue = request.vehicleModelName,
                    modifier = Modifier
                        .width(250.dp)
                )
                DashboardFieldButton(
                    fieldName = "Request ID",
                    fieldValue = "R${request.id}",
                    modifier = Modifier
                        .width(250.dp)
                )

                OutlinedTextField(
                    value = request.description,
                    onValueChange = { },
                    modifier = Modifier
                        .height(100.dp)
                        .width(250.dp)
                        .border(2.dp, Color.White, shape = RoundedCornerShape(20))
                        .shadow(2.dp, shape = RoundedCornerShape(20))
                        .background(Color.White),
                    enabled = false,
                    placeholder = {
                        Text(
                            text = serviceRequestViewModel.description.value.ifEmpty { "No Description Provided" },
                            fontSize = 12.sp,
                            color = Color(0xFF253555)
                        )
                    },
                    textStyle = TextStyle(
                        color = Color(0xFF253555)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}