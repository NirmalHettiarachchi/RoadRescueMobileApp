package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditTechnician(){
    Column(
        modifier = defaultBackground,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(menuClicked = {})

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Edit Technician", style = textStyle4, modifier = Modifier, fontSize = 26.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = cardDefaultModifier.align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
            border = BorderStroke(width = 2.dp, Color.White),
        ) {

        }

        Spacer(modifier = Modifier.height(26.dp))

        Footer()
    }
}