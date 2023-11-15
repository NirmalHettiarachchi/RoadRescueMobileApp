package eu.tutorials.roadrescuecustomer

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navigationToDashboardScreen: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    SidebarContent {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    ) {
        Scaffold {
            Column(
                backgroundModifier.padding(it),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    backgroundModifier,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Header {
                            scope.launch {drawerState.open()}
                        }
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
        }
    }
}

@Composable
fun ProfileBox() {
    val context = LocalContext.current

    var isEditing by remember { mutableStateOf(false) }

    var showPhoneNumDetailWindow by remember {mutableStateOf(false)}
    var showNumOfReqServiceWindow by remember {mutableStateOf(false)}

    var isCancelClicked by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("Nirmal Hettiarachchi") }
    var email by remember { mutableStateOf("nirmalhettiarachchi5@gmail.com") }
    val phoneNumber by remember { mutableStateOf("+94 768879830") }
    val numOfServiceReq by remember { mutableStateOf("2") }

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

            if(isCancelClicked) {
                name = profileField("Name", name, isEditing)
                email = profileField("Email", email, isEditing)
            } else {
                profileField("Name", name, isEditing)
                profileField("Email", email, isEditing)
            }

            ProfileFieldButton(
                labelName = "Phone Number",
                value = phoneNumber,
                onClickButton = {showPhoneNumDetailWindow = true}
            )
            ProfileFieldButton(
                labelName = "Number of Service Requests",
                value = numOfServiceReq,
                onClickButton = {showNumOfReqServiceWindow = true}
            )

            if(!isEditing) {
                //Edit button
                ProfileScreenButton(btnName = "Edit Profile", Modifier.align(Alignment.CenterHorizontally)) {
                    isCancelClicked = false
                    isEditing = true
                }
            } else {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    //Save Button
                    ProfileScreenButton("Save", Modifier) {
                        isEditing = false
                        isCancelClicked = false
                        Toast.makeText(context, "Changes saved successfully!", Toast.LENGTH_SHORT).show()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    //Cancel Button
                    ProfileScreenButton("Cancel", Modifier) {
                        isEditing = false
                        isCancelClicked = true
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
fun profileField(labelName: String, value: String, isEditing: Boolean = false):String {
    var fieldValue by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
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
                    .padding(horizontal = 36.dp)
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
    return fieldValue
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFieldButton(labelName: String, value: String, onClickButton: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = labelName,
                style = textStyle2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Card(
                onClick = { onClickButton() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(horizontal = 38.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC6D4DE))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                    , modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = value,
                        modifier = Modifier.padding(start = 8.dp).weight(1f),
                        maxLines = 1,
                        style = textStyle2,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.question_fill),
                        modifier = Modifier
                            .size(30.dp),
                        contentDescription = "Info",
                        tint = Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProfileScreenButton(btnName: String, modifier: Modifier, onClickButton: () -> Unit) {
    Button(
        onClick = { onClickButton() },
        modifier = modifier,
        border = BorderStroke(width = 2.dp, color = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
    ) {
        Text(
            text = btnName,
            style = textStyle3
        )
    }
}
