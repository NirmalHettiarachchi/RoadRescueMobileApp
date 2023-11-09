package eu.tutorials.roadrescuecustomer

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Dashboard() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFD3EFFF), Color.White),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            ),
    ) {
        //Welcome text
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome Nirmal Hettiarachchi",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                letterSpacing = 0.15.sp,
                color = Color(0xFF253555)
            )
        )

        //Request Service Box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color(0xFFB6C7E3), shape = RoundedCornerShape(percent = 10))
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(percent = 10)),
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
                    text = "Have you faced a vehicle breakdown? Just click on the button below...",
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        letterSpacing = 0.15.sp,
                        color = Color(0xFF253555),
                        textAlign = TextAlign.Center
                    )
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
                        horizontalArrangement = Arrangement.spacedBy(4.dp) // Adjust spacing between icon and text as needed
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward, // Replace 'YourIcon' with the desired icon from the Icons.Default class
                            contentDescription = null, // Provide a content description for accessibility (can be null if not needed)
                            tint = Color.White // Tint color of the icon
                        )
                        Text(
                            text = "Request Service",
                            style = TextStyle(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                letterSpacing = 0.15.sp,
                                color = Color.White,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(4.dp) // Apply shadow to the outer Box
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(2.dp, Color.White),
                        shape = RoundedCornerShape(percent = 10),
                    )
                    .background(color = Color(0xFFB6C7E3))
                    .padding(4.dp)
            ) {
                //common vehicle breakdown box
                Text(text = "/////Box for the common vehicle breakdowns")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(4.dp) // Apply shadow to the outer Box
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(2.dp, Color.White),
                        shape = RoundedCornerShape(percent = 10),
                    )
                    .background(color = Color(0xFFB6C7E3))
                    .padding(4.dp)
            ) {
                //hrlp box
                Text(text = "/////This is the hep box...")
            }}
    }
}

