package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthField(labelName: String, value: String?): String {
    var fieldValue by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fieldValue ?: "",
                onValueChange = { fieldValue = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .height(50.dp)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(50))
                    .shadow(6.dp, shape = RoundedCornerShape(50))
                    .background(Color.White),
                textStyle = textStyle2,
                placeholder = {
                    Text(
                        text = labelName,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    return fieldValue ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthFieldBtn(onClickButton: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                onClick = { onClickButton() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(horizontal = 38.dp, vertical = 8.dp)
                    .width(120.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC6D4DE))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "Send OTP",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        style = textStyle2,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun AuthCommonButton(btnName: String, modifier: Modifier, onClickButton: () -> Unit) {
    Button(
        onClick = { onClickButton() },
        modifier = modifier.width(200.dp),
        border = BorderStroke(width = 2.dp, color = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
    ) {
        Text(
            text = btnName,
            style = textStyle3, modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}
