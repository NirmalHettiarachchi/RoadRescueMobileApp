package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

val textStyle4 = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.15.sp,
    color = Color(0xFF253555),
)

val textStyle5 = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    letterSpacing = 0.15.sp,
    color = Color(0xFF253555),
)

val cardModifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 16.dp, vertical = 8.dp)

val backgroundModifier = Modifier
    .fillMaxSize()
    .background(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFD3EFFF), Color.White),
            start = Offset(0f, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY)
        )
    )