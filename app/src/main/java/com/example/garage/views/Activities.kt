package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.garage.viewModels.GarageActivityDetails
import com.example.garage.viewModels.GarageDashboardViewModel

@Composable
fun Activities(
    activityDetails:GarageActivityDetails
){
        Column (
            modifier = defaultBackground,
            horizontalAlignment = Alignment.CenterHorizontally,

        ){

            Header(menuClicked = {})

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Activities", style = textStyle4, modifier = Modifier, fontSize = 26.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = cardDefaultModifier.align(Alignment.CenterHorizontally).verticalScroll(rememberScrollState()),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
                border = BorderStroke(width = 2.dp, Color.White),
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    // for each ekk dala load karanna notification tika

                    Spacer(modifier = Modifier.height(16.dp))

                    ActivityCard(activityDetails,Modifier.align(Alignment.CenterHorizontally))

                    Spacer(modifier = Modifier.height(16.dp))

                    ActivityCard(activityDetails,Modifier.align(Alignment.CenterHorizontally))

                    Spacer(modifier = Modifier.height(16.dp))

                    ActivityCard(activityDetails,Modifier.align(Alignment.CenterHorizontally))

                    Spacer(modifier = Modifier.height(16.dp))
                }



            }

            Spacer(modifier = Modifier.height(26.dp))

            Footer()
        }
}


@Composable
fun ActivityCard(activityDetails:GarageActivityDetails, modifier: Modifier){
    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .shadow(elevation = 8.dp, shape = RectangleShape),
        colors = CardDefaults.cardColors(containerColor = Color.White),

        ) {

        var showDialog by remember { mutableStateOf(false) }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You have new service request ${activityDetails.getServiceRequestTime()}",
            color = Color(0xFF253555),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Divider(color = Color(0xFF253555), thickness = 2.dp)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("•  ")
                }
                append("Date")

            }, color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = "${activityDetails.getServiceRequestDate()}",color = Color.Black, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("•  ")
                }
                append("Customer Name")

            }, color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = activityDetails.getCustomerName(),color = Color.Black, modifier = Modifier.weight(1f), maxLines = 3)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("•  ")
                }
                append("Vehicle")

            }, color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = activityDetails.getVehicle(),color = Color.Black, modifier = Modifier.weight(1f),)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("•  ")
                }
                append("Technician Id")

            }, color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = activityDetails.getTechnicianId(),color = Color.Black, modifier = Modifier.weight(1f),)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("•  ")
                }
                append("Amount")

            }, color = Color.Black, modifier = Modifier
                .weight(1f)
                .padding(8.dp, 0.dp))
            Text(text = "LKR ${activityDetails.getAmount()}0",color = Color.Black, modifier = Modifier.weight(1f),)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                showDialog=true
            }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription ="Contact icon",
                    tint = Color.Black
                )
            }


            if (showDialog){
                Dialog(
                    onDismissRequest = {  },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .fillMaxHeight(0.4f)
                                .background(
                                    Color(0xFFACB3C0),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {



                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ){
                                IconButton(onClick = { showDialog = false  }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "close icon",
                                        modifier = closerButtonStyles
                                    )
                                }
                            }


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Date")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = "${activityDetails.getServiceRequestDate()}",color = Color.Black, modifier = Modifier.weight(1f))
                            }

                            Spacer(modifier = Modifier.height(8.dp))


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Time")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = "${activityDetails.getServiceRequestTime()}",color = Color.Black, modifier = Modifier.weight(1f))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Customer Name")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = activityDetails.getCustomerName(),color = Color.Black, modifier = Modifier.weight(1f), maxLines = 3)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Vehicle")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = activityDetails.getVehicle(),color = Color.Black, modifier = Modifier.weight(1f),)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Description ")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = activityDetails.getDescription(),color = Color.Black, modifier = Modifier.weight(1f), maxLines = 3)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Technician Id")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = activityDetails.getTechnicianId(),color = Color.Black, modifier = Modifier.weight(1f),)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Technician Name")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = activityDetails.getTechnicianName(),color = Color.Black, modifier = Modifier.weight(1f),)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    ) {
                                        append("•  ")
                                    }
                                    append("Amount")

                                }, color = Color.Black, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp))
                                Text(text = "${activityDetails.getAmount()}",color = Color.Black, modifier = Modifier.weight(1f),)
                            }

                            Spacer(modifier = Modifier.height(8.dp))


                        }
                    }
                )
            }
        }

    }
}