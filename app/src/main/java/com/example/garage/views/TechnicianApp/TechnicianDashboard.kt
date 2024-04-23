package com.example.garage.views.TechnicianApp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.garage.models.ResponseObject
import com.example.garage.models.TechnicianDashboard
import com.example.garage.repository.Screen
import com.example.garage.repository.TechnicianDashboardServiceCommonDetails
import com.example.garage.viewModels.LoginShearedViewModel
import com.example.garage.viewModels.MainViewModel
import com.example.garage.viewModels.TechnicianShearedViewModel
import com.example.garage.views.CircularProcessingBar
import com.example.garage.views.CommonButton
import com.example.garage.views.Header
import com.example.garage.views.defaultBackground
import com.example.garage.views.textStyle4
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.SocketTimeoutException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TechnicianDashboard(
    navController: NavController,
    navStatus: String,
    loginShearedViewModel: LoginShearedViewModel,
    technicianShearedViewModel: TechnicianShearedViewModel
){
    val techId=loginShearedViewModel.loginId
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLoadGarageDetails by remember { mutableStateOf(false) }
    var processingBarStatus = remember { mutableStateOf(true) }
    val viewModel:MainViewModel= viewModel()
    var responseString by remember { mutableStateOf("") }
    val context= LocalContext.current

    LaunchedEffect(Unit){
        if (techId != null) {
            var response =loadServices(viewModel,techId)
            if (response != null) {
                if (response.status == 200) {
                    Log.d("responce", response.data.toString())
                    responseString = response.data!!.toString()
                    processingBarStatus.value=false
                    showLoadGarageDetails = true

                } else if (response.status == 400) {
                    processingBarStatus.value=false
                    Toast.makeText(
                        context,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (response.status == 404) {
                    processingBarStatus.value=false
                    Toast.makeText(
                        context,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (response.status == 500) {
                    processingBarStatus.value=false
                    Toast.makeText(
                        context,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (response.status == 508) {
                    processingBarStatus.value=false
                    Toast.makeText(
                        context,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    processingBarStatus.value=false
                    Toast.makeText(
                        context,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                processingBarStatus.value=false
                Toast.makeText(
                    context,
                    "Cannot call the sever",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    TechnicianSliderContent(navController) {
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
                TechnicianFooter(navController, navStatus)
            }
        ) {

            CircularProcessingBar(isShow = processingBarStatus.value)

            Column(
                modifier = defaultBackground.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Welcome Sanath Nishnatha",
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

                    if (showLoadGarageDetails) {
                        Log.d("1", "TechnicianDashboard: 1")
                        if (responseString.isNotEmpty()) {
                            Log.d("2", "TechnicianDashboard: 2")
                            val jsonArray = JSONArray(responseString)
                            for (i in 0 until jsonArray.length()) {
                                Log.d("3", "TechnicianDashboard: 3")
                                val jsonObject = jsonArray.getJSONObject(i)
                                val time = jsonObject.getString("time")
                                val description = jsonObject.getString("description")
                                val issueCategory = jsonObject.getString("issueCategory")
                                val customerName = jsonObject.getString("customerName")
                                val customerContact = jsonObject.getString("customerContact")
                                val vehicleModel = jsonObject.getString("vehicleModel")
                                val serviceId = jsonObject.getString("serviceId")
                                val technicianService=TechnicianDashboard(serviceId,time,description,issueCategory,customerName,customerContact,vehicleModel)

                                Spacer(modifier = Modifier.height(16.dp))

                                if (techId != null) {
                                    ServiceRequest(
                                        techId,
                                        technicianService,
                                        navController,
                                        Modifier.align(Alignment.CenterHorizontally),
                                        context,
                                        technicianShearedViewModel,
                                        true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ServiceRequest(
    techId:String,
    technicianService:TechnicianDashboard,
    navController: NavController,
    modifier: Modifier,
    context: Context,
    technicianShearedViewModel: TechnicianShearedViewModel,
    button:Boolean
) {

    val phoneNumber = technicianService.getCustomerContact()


    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.35f)
            .shadow(elevation = 8.dp, shape = RectangleShape),
        colors = CardDefaults.cardColors(containerColor = Color.White),

        ) {

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = " ${technicianService.getIssueCategory()}",
                color = Color(0xFF253555),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "location",
                tint = Color.Black,
                modifier = Modifier
                    .padding(100.dp, 0.dp, 0.dp, 0.dp)
                    .clickable { }
            )

            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Contact icon",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    context.startActivity(intent)
                }
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(thickness = 2.dp, color = Color(0xFF253555))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Time", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = technicianService.getTime(),
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
                text = "Customer Name", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = technicianService.getCustomerName(),
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 2
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
                text = "Vehicle", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = technicianService.getVehicleModel(),
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 1
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
                text = technicianService.getDescription().ifEmpty { "No Description" },
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        if (button) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                CommonButton(
                    btnName = "Accept",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClickButton = {

                        val details= TechnicianDashboardServiceCommonDetails(
                            techId,
                            technicianService.getServiceId(),
                            technicianService.getTime(),
                            technicianService.getDescription(),
                            technicianService.getIssueCategory(),
                            technicianService.getCustomerName(),
                            technicianService.getCustomerContact(),
                            technicianService.getVehicleModel()
                        )
                        technicianShearedViewModel.techCommonDetails(details)
                        navController.navigate(route = Screen.TechnicianCompleteJob.route)
                    }
                )

            }
        }


        Spacer(modifier = Modifier.height(8.dp))
    }
}

suspend fun loadServices(viewModel: MainViewModel,techId:String):ResponseObject? {
    var response: ResponseObject? = null

    try {
        viewModel.getTechnicianServices(techId, "getTechServices") { responseObject ->
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