package eu.tutorials.roadrescuecustomer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    navigationToDashboardScreen: () -> Unit
) {
    Column(
        backgroundModifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Header()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Customer Profile",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle1
            )
            ProfileBox()
            HelpBox()
        }
        Footer(navigationToDashboardScreen) {}
    }
}

@Composable
fun ProfileBox() {
    var isEditing by remember { mutableStateOf(false) }

    var showPhoneNumDetailWindow by remember {mutableStateOf(false)}
    var showNumOfReqServiceWindow by remember {mutableStateOf(false)}

    val name by remember { mutableStateOf("Nirmal Hettiarachchi") }
    val email by remember { mutableStateOf("nirmalhettiarachchi5@gmail.com") }
    val phoneNumber by remember { mutableStateOf("+94 768879830") }
    val numOfServiceReqs by remember { mutableStateOf("2") }

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
            Spacer(modifier = Modifier.height(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.profile_pic),
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Unspecified, contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileField("Name", name, isEditing)
            ProfileField("Email", email, isEditing)
            ProfileFieldButton("Phone Number", phoneNumber, onClickButton = {showPhoneNumDetailWindow = true} )
            ProfileFieldButton("Number of Service Requests",numOfServiceReqs, onClickButton = {showNumOfReqServiceWindow = true})

            if(!isEditing) {
                //Edit button
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { isEditing = true },
                    border = BorderStroke(width = 2.dp, color = Color.White),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
                ) {
                    Text(
                        text = "Edit Profile",
                        style = textStyle3
                    )
                }
            } else {
                //Save and Cancel Button
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = { isEditing = false },
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
                    ) {
                        Text(
                            text = "Save",
                            style = textStyle3
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            isEditing = false },
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
                    ) {
                        Text(
                            text = "Cancel",
                            style = textStyle3
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
    if(showPhoneNumDetailWindow) {
        MoreInfoWindow(message = "You can change the registered phone number by accessing the settings...") {
            showPhoneNumDetailWindow = false
        }
    }
    if(showNumOfReqServiceWindow) {
        MoreInfoWindow(message = "This number shows only the completed service requests that have been accepted by a service provider and completed.") {
            showNumOfReqServiceWindow = false
        }
    }
}

@Composable
fun ProfileField(labelName: String, value: String, isEditing: Boolean = false) {
    var fieldValue by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = labelName,
                style = textStyle2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fieldValue,
                onValueChange = { fieldValue = it },
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(30))
                    .shadow(6.dp, shape = RoundedCornerShape(30))
                    .background(Color.White),
                textStyle = textStyle2,
                singleLine = true,
                enabled = isEditing
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ProfileFieldButton(labelName: String, value: String, onClickButton: () -> Unit) {
    Box(
        modifier = Modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = labelName,
                style = textStyle2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Button(
                onClick = { onClickButton() },
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                modifier = Modifier
                    .width(250.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = value,
                        style = textStyle2,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Info",
                        tint = Color(0xFF253555)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
