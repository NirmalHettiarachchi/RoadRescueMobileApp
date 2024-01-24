package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.garage.R

@Composable
fun TechnicianProfile(){

    Column(
        modifier = defaultBackground,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var textTechName by remember { mutableStateOf("") }
        var textTechContactNub by remember { mutableStateOf("") }

        Header(menuClicked = {})

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Technician Profile", style = textStyle4, modifier = Modifier, fontSize = 26.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = cardDefaultModifier
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
            border = BorderStroke(width = 2.dp, Color.White),
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Row (
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Card (
                        shape = CircleShape,
                        modifier = Modifier
                            .background(Color.Red, shape = CircleShape)
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(0.93f)
                            .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.profile_pitcher),
                            contentDescription = "Technician Pitcher",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape)
                        )
                    }
                }

                Column (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    val expertiseAreas= listOf<String>("Oil & filter change","Engine Repair","Break System Repair","Break System Repair")

                    Text(
                        text = "Name",
                        style = textStyle1,
                        modifier = Modifier.padding(0.dp,0.dp,230.dp,0.dp)
                    )


                    CommonTextField(value = textTechName, isEditing = false, placeholderName = "Technician Name", modifier =Modifier )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Expertise Areas",
                        style = textStyle1,
                        modifier = Modifier.padding(0.dp,0.dp,155.dp,0.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    
                    CommonDropdown(optionList = expertiseAreas, defaultSelection = "Expertise Areas")

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Phone Number",
                        style = textStyle1,
                        modifier = Modifier.padding(0.dp,0.dp,155.dp,0.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CommonTextField(value = textTechContactNub, isEditing = false, placeholderName = "Phone Number", modifier =Modifier.height(52.dp) )
                }
            }

        }

        Spacer(modifier = Modifier.height(26.dp))
        Footer()

    }
}