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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    navigationToProfileScreen: () -> Unit,
    navigationToTrackLocationScreen: () -> Unit,
    currentStateViewModel: CurrentStateViewModel
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
                Column {
                    Header {
                        scope.launch {drawerState.open()}
                    }
                    //Welcome text
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Welcome Nirmal Hettiarachchi",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = textStyle1
                    )
                    RequestServiceBox(currentStateViewModel)
                    CommonIssuesBox(currentStateViewModel)
                    HelpBox()
                }
                Footer({}, navigationToProfileScreen, navigationToTrackLocationScreen)
            }
        }
    }
}

@Composable
fun RequestServiceBox(currentStateViewModel: CurrentStateViewModel) {
    var showRequestServiceWindow by remember { mutableStateOf(false) }

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
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { showRequestServiceWindow = true },
                border = BorderStroke(width = 2.dp, color = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
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
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    //Request service window
    if(showRequestServiceWindow) {
        RequestServiceWindow(onDismiss = {showRequestServiceWindow = false}, currentStateViewModel = currentStateViewModel)
    }
}

@Composable
fun RequestServiceWindow(onDismiss: () -> Unit, issueValue: String? = null, currentStateViewModel: CurrentStateViewModel) {
    var issue by remember { mutableStateOf("") }
    var vehicleType by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var showCostDetailWindow by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { },
        tonalElevation = 16.dp,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFB6C7E3)
            ),
        containerColor = Color(0xFFB6C7E3),
        confirmButton = {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close_round_fill),
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

                val issueList =  listOf("Fuel Issues", "Engine Overheating", "Flat Tire", "Dead Battery", "Other")
                val vehicleTypeList = listOf("Car", "Van", "Lorry", "Bicycle")
                val fuelTypeList = listOf("Petrol", "Diesel", "Hybrid", "Electric")

                issue = if(issueValue == null) {
                    dropDown("Issue", issueList)
                } else {
                    dropDown(dropDownText = issueValue, dropDownListItems = issueList)
                }
                vehicleType = dropDown("Vehicle Type", vehicleTypeList)
                fuelType = dropDown("Fuel Type", fuelTypeList)

                Button(
                    onClick = { showCostDetailWindow = true },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    border = BorderStroke(width = 2.dp, color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Cost LKR 0.00",
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
                Button(
                    onClick = { currentStateViewModel.setCurrentState(true) },
                    border = BorderStroke(width = 2.dp, color = Color.White),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
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
        }
    )
    if(showCostDetailWindow) {
        MoreInfoWindow (
            "The cost provided is an approximation based on the issue category, vehicle type, and fuel type you have provided. The actual amount may vary.",
            onDismiss = {showCostDetailWindow = false}
        )
    }
}
@Composable
fun dropDown(dropDownText: String, dropDownListItems: List<String>): String {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(dropDownText) }

    Box {
        Button(
            onClick = { isExpanded = true },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedValue,
                    color = Color(0xFF253555)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color(0xFF253555)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(270.dp)
                .background(Color.White),
        ) {
            dropDownListItems.forEachIndexed { index, dropDownListItem ->
                DropdownMenuItem(
                    text = {Text(text = dropDownListItem, color = Color(0xFF253555))},
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onClick = {
                        isExpanded = false
                        selectedValue = dropDownListItem
                    }
                )
                if (index < dropDownListItems.size - 1) {
                    Divider()
                }
            }
        }
    }
    return selectedValue
}

@Composable
fun CommonIssuesBox(currentStateViewModel: CurrentStateViewModel) {
    var showRequestServiceWindow by remember { mutableStateOf(false) }
    var selectedIssue by remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // First row, first button
                    CommonIssueButton(
                        issueCategory = "Fuel Issues",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            showRequestServiceWindow = true
                            selectedIssue = "Fuel Issues"}
                    )
                    // First row, second button
                    CommonIssueButton(
                        issueCategory = "Engine Overheating",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            showRequestServiceWindow = true
                            selectedIssue = "Engine Overheating"
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Second row, first button
                    CommonIssueButton(
                        issueCategory = "Flat Tire",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            showRequestServiceWindow = true
                            selectedIssue = "Flat Tire"
                        }
                    )

                    // Second row, second button
                    CommonIssueButton(
                        issueCategory = "Dead Battery",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClickButton = {
                            showRequestServiceWindow = true
                            selectedIssue = "Dead Battery"
                        }
                    )
                }
                Button(onClick = {
                    showRequestServiceWindow = true
                    selectedIssue = "Other" },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp), modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                    Text(
                        text = "Other",
                        color = Color(0xFF253555),
                        style = textStyle3.copy(textAlign = TextAlign.Center)
                    )
                }
            }
        }
    }
    if(showRequestServiceWindow) {
        RequestServiceWindow(onDismiss = {showRequestServiceWindow = false}, selectedIssue, currentStateViewModel = currentStateViewModel)
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