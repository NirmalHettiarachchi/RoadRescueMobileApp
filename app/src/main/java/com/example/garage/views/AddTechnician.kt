package com.example.garage.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.garage.viewModels.CheckBoxDetailsModel

@Composable
fun AddTechnician() {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    Spacer(modifier = Modifier.height(16.dp))
                    CommonTextField(textFirstName, true, "First Name",Modifier.weight(1f),false)
                    Spacer(modifier = Modifier.height(16.dp))

                    CommonTextField(textLastName, true, "Last Name",Modifier.weight(1f),false)
                    Spacer(modifier = Modifier.height(16.dp))

                    CommonTextField(textContactNumber, true, "Contact Number",Modifier.weight(1f),false)
                    Spacer(modifier = Modifier.height(16.dp))

                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Expertise Areas",
                    style = textStyle4,
                    modifier = Modifier.padding(0.dp,0.dp,100.dp,0.dp),
                    fontSize = 26.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ){
                    val isCheckedBreakSystem by remember { mutableStateOf(false) }
                    val checkboxColor = if(isCheckedBreakSystem) Color(0xFF253555) else Color.White

                    val servicesList= ArrayList<CheckBoxDetailsModel>()
                    servicesList.add(CheckBoxDetailsModel("Break System Repair", false))
                    servicesList.add(CheckBoxDetailsModel("Oil Change",false))
                    servicesList.add(CheckBoxDetailsModel("Tire Replacement",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))
                    servicesList.add(CheckBoxDetailsModel("Engine Repair",false))

                    servicesList.forEach{service->

                        Spacer(modifier = Modifier.height(8.dp))

                        var isChecked by remember { mutableStateOf(service.getIsSelected()) }

                        Row {

                            Spacer(modifier = Modifier.width(8.dp))

                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    if (isChecked==false){
                                        isChecked = true
                                    }else{
                                        isChecked = false
                                    }

                                    service.setIsSelected(isChecked)
                                },
                                modifier = Modifier
                                    .background(color = checkboxColor)
                                    .size(20.dp)
                                    .padding(4.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = service.getCheckBoxName(),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily
                            )

                        }

                    }

                }

                 Spacer(modifier = Modifier.height(16.dp))

                CommonButton(btnName = "Register", modifier = Modifier, onClickButton = {
                    
                })

                Spacer(modifier = Modifier.height(16.dp))
            }

        }
        Spacer(modifier = Modifier.height(26.dp))
        Footer()
    }
}
