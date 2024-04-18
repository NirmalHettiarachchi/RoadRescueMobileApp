package com.example.garage.views.TechnicianApp

import android.os.Build
import android.util.Log
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
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.garage.models.ServicesRequestModel
import com.example.garage.repository.Screen
import com.example.garage.views.CommonButton
import com.example.garage.views.Header
import com.example.garage.views.SidebarContent
import com.example.garage.views.defaultBackground
import com.example.garage.views.textStyle4
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TechnicianDashboard(
    navController: NavController,
    navStatus: String,
){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                TechnicianFooter(navController, navStatus)
            }
        ) {

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

                    Spacer(modifier = Modifier.height(16.dp))

                    ServiceRequest(
                        navController,
                        Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ServiceRequest(
                        navController,
                        Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ServiceRequest(
                        navController,
                        Modifier.align(Alignment.CenterHorizontally)
                    )

                }
            }

        }}
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ServiceRequest(navController: NavController,modifier: Modifier) {

    val garageDetails = ServicesRequestModel(
        "Nirmal Dakshina", "date",
        "Tire Punch", "Need help as soon as possible", 25000.00,"paka"
    )
    val phoneNumber = "0716788537"
    val context = LocalContext.current


    

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
                text = " ${garageDetails.getIssue()}",
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
                modifier = Modifier.clickable {  }
            )

        }

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
                text = "Time", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = garageDetails.getIssue(), //time ek danna
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
                text = garageDetails.getDescription(),
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
                text = "LKR ${garageDetails.getServiceFee()}0",
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
                text = "Description", color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 0.dp)
            )
            Text(
                text = "LKR ${garageDetails.getServiceFee()}0",
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            CommonButton(
                btnName = "Accept",
                modifier = Modifier.align(Alignment.CenterVertically),
                onClickButton = {
                    navController.navigate(route = Screen.TechnicianCompleteJob.route)
                }
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun getData() {
    Log.d("delay checked","delay is ok ")
}
