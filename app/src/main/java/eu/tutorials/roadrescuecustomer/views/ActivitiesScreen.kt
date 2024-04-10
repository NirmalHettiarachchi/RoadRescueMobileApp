package eu.tutorials.roadrescuecustomer.views

import android.graphics.Paint.Align
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.R
import kotlinx.coroutines.launch

@Composable
fun ActivitiesScreen() {

    val numOfActivities by remember {
        mutableIntStateOf(1)
    }

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
                WithActivityActivitiesScreen()
            }
        }
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
                text = "You don't have any activities yet",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle2
            )
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@Composable
fun WithActivityActivitiesScreen() {
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
            ServiceRequestCard()
            ServiceRequestCard()
            ServiceRequestCard()
            ServiceRequestCard()
            ServiceRequestCard()
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceRequestCard() {

    var showMoreInfoWindow by remember {
        mutableStateOf(false)
    }

    if(showMoreInfoWindow) {
        MoreInfoActivityWindow {
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
            text = "28/10/2023 5:31pm",
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
            Text(text = ":Completed", style = textStyle4, modifier = Modifier.weight(1f))
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
            Text(text = ":Super Garage", style = textStyle4, modifier = Modifier.weight(1f))
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
            Text(text = ":LKR 3500.00", style = textStyle4, modifier = Modifier.weight(1f))
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
fun MoreInfoActivityWindow(onDismiss: () -> Unit) {

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
                    text = "28/10/2023 5.31pm",
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
                    Text(text = ":S424", style = textStyle4, modifier = Modifier.weight(1f))
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
                    Text(text = ":Completed", style = textStyle4, modifier = Modifier.weight(1f))
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
                    Text(text = ":Super Garage", style = textStyle4, modifier = Modifier.weight(1f))
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
                    Text(text = ":LKR 3500.00", style = textStyle4, modifier = Modifier.weight(1f))
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
                    Text(text = ":Engine Fault", style = textStyle4, modifier = Modifier.weight(1f))
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
                    Text(text = ":Honda Fit (Hybrid)", style = textStyle4, modifier = Modifier.weight(1f))
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
                    Text(text = ":-", style = textStyle4, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}