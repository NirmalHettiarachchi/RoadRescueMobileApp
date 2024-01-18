package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.garage.R
import com.example.garage.viewModels.GarageProfileEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun garageProfileEdit(){


       // Header()

        Column(
            modifier = defaultBackground,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {



            Card(
                modifier = cardDefaultModifier,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
                border = BorderStroke(width = 2.dp, Color.White),
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                ) {

                    var textFirstName by remember{ mutableStateOf("") }
                    var textLastName by remember{ mutableStateOf("") }
                    var garageName by remember{ mutableStateOf("") }
                    var contactNumber by remember{ mutableStateOf("") }
                    var email by remember { mutableStateOf("") }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Card(
                            shape = CircleShape,
                            border = BorderStroke(width = 2.dp, color = Color.White),
                            modifier = Modifier
                                .padding(8.dp, 16.dp, 8.dp, 8.dp)
                                .fillMaxHeight(0.25f)
                                .fillMaxWidth(0.5f)
                                .border(BorderStroke(2.dp, Color(0xFF253555)), shape = CircleShape)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.profile_pitcher),
                                contentDescription = "my pitcher",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .background(Color(0xDFFFFFFF))

                            )
                        }

                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.padding(0.dp, 120.dp, 0.dp, 0.dp)

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "edit icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color(0xFF253555), shape = RoundedCornerShape(5.dp))
                                    .border(
                                        BorderStroke(0.dp, Color.White),
                                        shape = RoundedCornerShape(5.dp)
                                    )

                            )

                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15f)
                            .weight(0.5f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextFieldModifier(textFirstName, true, "First Name")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15f)
                            .weight(0.5f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextFieldModifier(textLastName, true, "Last Name")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15f)
                            .weight(0.5f)
                    ) {
                        TextFieldModifier(garageName, true, "Garage Name")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15f)
                            .weight(0.5f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextFieldModifier(contactNumber, false, "Contact number")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15f)
                            .weight(0.5f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextFieldModifier(email, true, "Email")
                    }


                    //-----------------------------------------------------------------

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1.5f)
                            .padding(16.dp, 0.dp, 0.dp, 0.dp)
                    ){
                        Column {
                            val isCheckedBraekSysytem by remember { mutableStateOf(false) }
                            var isCheckedOilChange by remember { mutableStateOf(false) }
                            var isCheckedEngineRepair by remember { mutableStateOf(false) }
                            var isCheckedTireRepair by remember { mutableStateOf(false) }

                            Text(
                                text = "Services",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 26.sp,
                                textDecoration = TextDecoration.Underline
                            )

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.75f),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                val checkboxColor = if(isCheckedBraekSysytem) Color(0xFF253555) else Color.White

                                val servicesList= ArrayList<GarageProfileEditViewModel>()
                                servicesList.add(GarageProfileEditViewModel("Break System Repair", false))
                                servicesList.add(GarageProfileEditViewModel("Oil Change",false))
                                servicesList.add(GarageProfileEditViewModel("Tire Replacement",false))
                                servicesList.add(GarageProfileEditViewModel("Engine Repair",false))


                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                ){
                                    items(servicesList){ service ->
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .align(Alignment.CenterVertically)
                                        ){

                                            var isChecked by remember { mutableStateOf(service.getIsSelected()) }

                                            Checkbox(
                                                checked = service.getIsSelected(),
                                                onCheckedChange ={ newCheckState -> isChecked = newCheckState },
                                                modifier = Modifier
                                                    .background(color = checkboxColor)
                                                    .size(20.dp)
                                                    .padding(4.dp)
                                                    .align(Alignment.CenterStart)
                                            )

                                            Spacer(modifier = Modifier.width(8.dp))

                                            Text(
                                                text = service.getCheckBoxName(),
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(24.dp,0.dp,0.dp,0.dp),
                                                fontFamily = fontFamily
                                            )
                                        }
                                    }
                                }
                            }


                        }
                    }

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15f),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Spacer(modifier = Modifier.width(8.dp))

                        CommonButton(btnName = "Cancel", modifier = Modifier.weight(1f)) {}

                        Spacer(modifier = Modifier.width(16.dp))

                        CommonButton(btnName = "Save", modifier = Modifier.weight(1f)) {}

                        Spacer(modifier = Modifier.width(8.dp))
                    }

                }

            }
            //Footer()
        }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldModifier(value:String,isEditing:Boolean,placeholderName:String):String{

    var textFieldValue by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.9f),
        contentAlignment = Alignment.Center
    ){
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {textFieldValue = it },
            textStyle = TextStyle(
                Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize =16.sp,
                letterSpacing = 0.15.sp,
                textAlign = TextAlign.Center,
                fontFamily = fontFamily
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor =Color.Transparent
            ),
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(0.dp, Color.Unspecified),
                    shape = RoundedCornerShape(50.dp)
                )
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Max),
            enabled = isEditing,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholderName,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF253555),
                    modifier = Modifier.padding(85.dp,0.dp,0.dp,0.dp),
                    fontFamily= fontFamily
                )
            },
        )
    }

    return textFieldValue
}