package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterTechnician() {
    Column(
        modifier = defaultBackground,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(menuClicked = {})

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Register Technician",
            style = textStyle4,
            modifier = Modifier,
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = cardDefaultModifier
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
            border = BorderStroke(width = 2.dp, Color.White),
        ) {
            var textFirstName by remember { mutableStateOf("") }
            var textLastName by remember { mutableStateOf("") }
            var textContactNumber by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Column (
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ){
                    Spacer(modifier = Modifier.height(16.dp))
                    CommonTextField(textFirstName, true, "First Name",Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(16.dp))

                    CommonTextField(textLastName, true, "Last Name",Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(16.dp))

                    CommonTextField(textContactNumber, true, "Contact Number",Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(16.dp))

                }

                Text(
                    text = "Expertise Areas",
                    style = textStyle4,
                    modifier = Modifier.padding(0.dp,0.dp,100.dp,0.dp),
                    fontSize = 26.sp
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth().weight(1f)
                        .background(Color.Yellow)
                ){
                    CommonButton(btnName = "Naaki Hukana", modifier = Modifier) {

                    }
                }

                 Spacer(modifier = Modifier.height(16.dp))



            }
            CommonButton(btnName = "Playa", modifier = Modifier) {

            }

        }
        Spacer(modifier = Modifier.height(26.dp))
        Footer()
    }
}
