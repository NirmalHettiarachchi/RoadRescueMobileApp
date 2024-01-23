package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                            contentDescription = "my pitcher",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape)
                        )
                    }
                }

                Box (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ){
                    //

                }
            }

        }

        Spacer(modifier = Modifier.height(26.dp))
        Footer()

    }
}