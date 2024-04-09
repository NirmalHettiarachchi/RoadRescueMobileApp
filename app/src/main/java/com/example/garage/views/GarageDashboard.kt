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
import com.example.garage.repository.GarageCommonDetails
import com.example.garage.viewModels.GarageDashboardViewModel
import com.example.garage.viewModels.MainViewModel
import com.example.garage.viewModels.SharedViewModel
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.time.Period

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GarageDashboard(
    navController: NavController,
    navStatus:String,
    sharedViewModel: SharedViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel= viewModel<MainViewModel>()

    var showLoadGarageDetails by remember { mutableStateOf(false) }
    var showMessageDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }
    var garage by remember { mutableStateOf("") }
    var garageDetailsBackend=Garage()

    val technicians = listOf<String>("Saman Kumara","Tharindu Dakshina","Ajith Muthukumara","Namal Rajapakasha")

    LaunchedEffect(Unit){
        val response=loadGarageDetails(viewModel)
        if (response != null) {
            if(response.status ==200){

                garage= response.data!!.toString()
//                showProgressBar=false
                showLoadGarageDetails=true

            }else if(response.status==400){
                title=response.status.toString()
                message= response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showMessageDialog=true

            }else if(response.status==404){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showMessageDialog=true

            }else if(response.status==500){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showMessageDialog=true
            }else if(response.status==508){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="null"
                buttonTwoName="null"
                showMessageDialog=true
            }else{
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showMessageDialog=true
            }
        }else{
            title="401"
            message="Cannot call the sever"
            buttonOneName="Ok"
            buttonTwoName="null"
            showMessageDialog=true
            Log.d("response null","null")
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

            garageDetailsBackend.setGarageName(garageName)
            garageDetailsBackend.setOwnerName(ownerName)
            garageDetailsBackend.setGarageContactNumber(garageContactNumber)
            garageDetailsBackend.setGarageStatus(garageStatus)
            garageDetailsBackend.setGarageEmail(garageEmail)
            garageDetailsBackend.setGarageRating(garageRating)
            garageDetailsBackend.setGarageType(garageType)


        }catch (e: JSONException){
            e.localizedMessage?.let { it1 -> Log.d("json error", it1) }
        }

    }

    val garageCommonDetails= GarageCommonDetails(
        "1",
        garageDetailsBackend.getGarageName(),
        garageDetailsBackend.getGarageContactNumber(),
        garageDetailsBackend.getGarageStatus(),
        garageDetailsBackend.getGarageEmail(),
        garageDetailsBackend.getGarageRating().toString(),
        garageDetailsBackend.getGarageType(),
        garageDetailsBackend.getOwnerName()
    )


    navController.currentBackStackEntry?.savedStateHandle?.set(
        key = "garageDetails",
        value = garageCommonDetails
    )


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    SidebarContent(){
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    ) {
        Scaffold (
            topBar = {Header {
                scope.launch { drawerState.open() }
            }},
            bottomBar = {
                Footer(navController,navStatus)
            }
        ){
            Column (
                modifier = defaultBackground.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,

                ){




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
                        .fillMaxHeight(0.85f)
                        .verticalScroll(state = rememberScrollState()),
                    shape= RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
                    border = BorderStroke(width = 2.dp, Color.White),

                    ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    // Load are service requests
                    // custommer request load karanna one
                    ServiceRequest(garageDetailsBackend,technicians,Modifier.align(Alignment.CenterHorizontally))

                    Spacer(modifier = Modifier.height(16.dp))

                    ServiceRequest(garageDetailsBackend,technicians,Modifier.align(Alignment.CenterHorizontally))
                }

                Spacer(modifier = Modifier.height(24.dp))

            }

            if (showMessageDialog){
                sweetAlertDialog(
                    title = title,
                    message = message,
                    buttonOneName = buttonOneName,
                    buttonTwoName = buttonTwoName,
                    onConfirm = {
                        showMessageDialog=false
                    }
                )
            }
        }
    }
}

suspend fun loadGarageDetails(viewModel: MainViewModel): ResponseObject? {
    var response: ResponseObject? =null

    try {
        viewModel.getGarageDetails("1","search"){responseObject ->
            if (responseObject!=null) {
                Log.d("responseBody",responseObject.toString())
                response=responseObject
            }else{
                response= ResponseObject(400,"response is null",null)
            }
        }
    }catch (e:SocketTimeoutException){
        response=ResponseObject(508,"Request time out.\n Please try again.",e.localizedMessage)
    }catch (e:Exception){
        response=ResponseObject(404 ,"Exception error.",e.localizedMessage)
    }
    return  response
}





// meke data tika load karanna one custommerge trigger eka dala

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ServiceRequest(garageDetails:Garage, technicianList:List<String>,modifier: Modifier){

    val garageDetails = GarageDashboardViewModel(
        "Nirmal Dakshina", Period.of(1, 2, 3),
        "Tire Punch", "Need help as soon as possible", 25000.00
    )


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
            text = "You have new service request ${garageDetails.getDate()}",
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
            Text(text = "Issue", color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = garageDetails.getStatus(),color = Color.Black, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Description", color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = garageDetails.getAssignServiceProvider(),color = Color.Black, modifier = Modifier.weight(1f), maxLines = 3)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Service Fees(approx..)", color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = "LKR ${garageDetails.getServiceFee()}0",color = Color.Black, modifier = Modifier.weight(1f),)
        }

        Spacer(modifier = Modifier.height(8.dp))

        val phoneNumber="0716788537"
        val context = LocalContext.current


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {

                val intent= Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

                context.startActivity(intent)

            }) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription ="Contact icon",
                    tint = Color.Black
                )
            }

            CommonButton(
                btnName = "Accept",
                modifier = Modifier.align(Alignment.CenterVertically),
                onClickButton = {showDialog=true}
            )

            if (showDialog){
                Dialog(
                    onDismissRequest = {  },
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



                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ){
                                IconButton(onClick = { showDialog = false  }) {
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

                            CommonDropdown(
                                optionList = technicianList,
                                defaultSelection = "Technician "
                            )


                            Spacer(modifier = Modifier.height(64.dp))

                            // accept button load


                            CommonButton(
                                btnName = "Accept",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClickButton = {

/*
//                                    garageViewModel.fetchBackend()
                                    Log.d("rsp","request is ok ")

//                                    val  viewState by garageViewModel.backendState

                                    when{

                                        viewState!!.loading -> {
                                            // loading  wanna mona hari danna
                                            Log.d("loading","${viewState?.loading}")
                                        }

                                        viewState?.error !=null ->{
                                            viewState?.error!!.message?.let { Log.d("err", "it") }
                                        }

                                        viewState?.response !=null -> {
                                            Log.d("data final","${viewState?.response!!.data}")
                                        }
                                    }*/

                                }
                            )

                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}



