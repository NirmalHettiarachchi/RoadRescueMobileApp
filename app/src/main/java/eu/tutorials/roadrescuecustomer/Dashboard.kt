package eu.tutorials.roadrescuecustomer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val textStyle1 = TextStyle(
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    letterSpacing = 0.15.sp,
    color = Color(0xFF253555)
)

val textStyle2 = TextStyle(
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    letterSpacing = 0.15.sp,
    color = Color(0xFF253555),
)

val textStyle3 = TextStyle(
    fontWeight = FontWeight.ExtraBold,
    fontSize = 16.sp,
    letterSpacing = 0.15.sp,
    color = Color.White,
)

val cardModifier = Modifier
    .fillMaxWidth()
    .padding(16.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFD3EFFF), Color.White),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            CenterAlignedTopAppBar(
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
                        modifier = Modifier.size(100.dp),
                        tint = Color.Unspecified,
                        contentDescription = "Toolbar icon"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Localized description"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF253555)
                ),
            )
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
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                .background(color = Color(0xFF253555))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.book_fill),
                modifier = Modifier
                    .padding(16.dp)
                    .size(45.dp),
                tint = Color.Unspecified, contentDescription = null
            )
            Icon(
                painter = painterResource(id = R.drawable.compass_fill),
                modifier = Modifier
                    .padding(16.dp)
                    .size(45.dp),
                tint = Color.Unspecified, contentDescription = null
            )
            Icon(
                painter = painterResource(id = R.drawable.home),
                modifier = Modifier
                    .padding(16.dp)
                    .size(45.dp),
                tint = Color.Unspecified, contentDescription = null
            )
            Icon(
                painter = painterResource(id = R.drawable.chat_fill),
                modifier = Modifier
                    .padding(16.dp)
                    .size(45.dp),
                tint = Color.Unspecified, contentDescription = null
            )
            Icon(
                painter = painterResource(id = R.drawable.user_fill),
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp),
                tint = Color.Unspecified, contentDescription = null
            )


        }
    }
}

@Composable
fun RequestServiceBox() {
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
                onClick = {},
                border = BorderStroke(width = 2.dp, color = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp) // Adjust spacing between icon and text
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.send_fill),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .size(30.dp),
                        tint = Color.Unspecified // Tint color of the icon
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
}

@Composable
fun CommonIssuesBox() {
    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White), shape = RoundedCornerShape(20.dp),
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
                Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
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

@Composable
fun HelpBox() {
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
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "How to use the system?",
                    style = textStyle2,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_drop_right),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}