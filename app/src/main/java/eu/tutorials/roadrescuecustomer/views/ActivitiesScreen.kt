package eu.tutorials.roadrescuecustomer.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.ServiceRequest
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivitiesScreen(serviceRequestViewModel: ServiceRequestViewModel) {

    val customerId = AppPreferences(LocalContext.current).getStringPreference("CUSTOMER_ID", "")
    LaunchedEffect(key1 = customerId) {
        serviceRequestViewModel.fetchRequestCount(customerId)
    }

    // Collect StateFlow as state in Compose
    val numOfActivities = serviceRequestViewModel.requestCount.collectAsState().value

    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Activities",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle1
            )
            if(numOfActivities == 0) {
                NoActivityActivitiesScreen()
            } else {
                WithActivityActivitiesScreen(serviceRequestViewModel)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun NoActivityActivitiesScreen() {
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
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "There are no activities to show!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WithActivityActivitiesScreen(serviceRequestViewModel: ServiceRequestViewModel) {
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
            val loading by serviceRequestViewModel.loading
            CircularProgressBar(isDisplayed = loading)

            val customerId = AppPreferences(LocalContext.current).getStringPreference("CUSTOMER_ID", "")
            LaunchedEffect(Unit) {
                serviceRequestViewModel.clearRequests()
                serviceRequestViewModel.fetchRequests(customerId)
            }

            serviceRequestViewModel.requests.forEach { request ->
                ServiceRequestCard(request)
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceRequestCard(serviceRequest: ServiceRequest) {

    var showMoreInfoWindow by remember {
        mutableStateOf(false)
    }

    val dateTime = LocalDateTime.parse(serviceRequest.date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
    val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    val statusText = when (serviceRequest.status.toInt()) {
        1, 2, 3 -> "Pending"
        4, 5 -> "Completed"
        6 -> "Canceled by the service provider"
        else -> "Canceled by the customer support"
    }

    if(showMoreInfoWindow) {
        MoreInfoActivityWindow(serviceRequest, formattedDate, formattedTime, statusText) {
            showMoreInfoWindow = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.35f)
            .shadow(elevation = 8.dp, shape = RectangleShape),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {showMoreInfoWindow = true}
        ) {

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$formattedDate at $formattedTime",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = textStyle2
        )
        Spacer(modifier = Modifier.height(4.dp))

        Divider(color = Color(0xFF253555), thickness = 1.dp)

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Status ", style = textStyle4,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(text = ":$statusText", style = textStyle4, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Service Provider ", style = textStyle4,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(text = ":${serviceRequest.serviceProviderName}", style = textStyle4, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Service Fees ", style = textStyle4,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(text = ":LKR ${serviceRequest.reqAmount ?: "0.00"}", style = textStyle4, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "", style = textStyle4,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.question_fill),
                contentDescription = "Info",
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp),
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun MoreInfoActivityWindow(serviceRequest: ServiceRequest, formattedDate: String, formattedTime: String, statusText: String, onDismiss: () -> Unit) {

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
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "$formattedDate at $formattedTime",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Request ID ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":R${serviceRequest.id}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Status ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":$statusText", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Service Provider ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${serviceRequest.serviceProviderName}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Service Fees ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":LKR ${serviceRequest.reqAmount ?: "0.00"}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Issue ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${serviceRequest.issueCategoryName}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vehicle ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${serviceRequest.vehicleModelName}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Description ", style = textStyle4,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp, 0.dp)
                    )
                    Text(text = ":${serviceRequest.description}", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}