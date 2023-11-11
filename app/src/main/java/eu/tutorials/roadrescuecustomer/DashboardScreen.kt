package eu.tutorials.roadrescuecustomer

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Dashboard() {
    Column(
        backgroundModifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Header()
            //Welcome text
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome Nirmal Hettiarachchi",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle1
            )
            RequestServiceBox()
            CommonIssuesBox()
            HelpBox()
        }
        Footer()
    }
}

@Composable
fun RequestServiceBox() {
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

    //RequestServiceWindow
    if(showRequestServiceWindow) {
        AlertDialog(onDismissRequest = { },
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
                        IconButton(onClick = { showRequestServiceWindow = false }) {
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
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .height(100.dp), // Adjust the height as needed
                        placeholder = {
                            Text("Write a Description (Optional) ... ", fontSize = 12.sp)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {},
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
    }
}


@Composable
fun CommonIssuesBox() {
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
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClick = {},
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
                    ) {
                        Text(
                            text = "Mechanical",
                            style = textStyle3.copy(textAlign = TextAlign.Center)
                        )
                    }
                    // First row, second button
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
                    ) {
                        Text(
                            text = "Electrical & Battery",
                            style = textStyle3.copy(textAlign = TextAlign.Center)
                        )
                    }
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
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
                    ) {
                        Text(
                            text = "Tire & Wheel",
                            style = textStyle3.copy(textAlign = TextAlign.Center)
                        )
                    }

                    // Second row, second button
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(8.dp),
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
                    ) {
                        Text(
                            text = "Fuel & Ignition",
                            style = textStyle3.copy(textAlign = TextAlign.Center)
                        )
                    }
                }
                Button(onClick = {},
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
}
