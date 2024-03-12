package com.example.garage.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.garage.R
import com.example.garage.models.CheckBoxDetailsModel

@Composable
fun EditTechnician(
    navController: NavController, navyStatus:String
){

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var textTechFirstName by remember { mutableStateOf("") }
    var textTechLastName by remember { mutableStateOf("") }
    var photoPickerLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult ={
            selectedImageUri=it
        }
    )

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
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
                            .background(Color.Unspecified, shape = CircleShape)
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(0.93f)
                            .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape)
                    ){


                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Unspecified)
                                .clip(CircleShape)
                                .clickable { }
                                .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape),
                            model = if(selectedImageUri==null)
                            {
                                R.drawable.user_fill
                            }else{
                                selectedImageUri
                                 },
                            contentDescription = "Technician Pitcher",
                            contentScale = ContentScale.Crop,

                            )
                    }

                    Icon(imageVector = Icons.Rounded.Edit,
                        contentDescription = "edit Image",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Bottom)
                            .background(Color(0xFF253555), shape = RoundedCornerShape(8.dp))
                            .clickable {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))


                Column (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CommonTextField(
                        value = textTechFirstName,
                        isEditing = true,
                        placeholderName = "First Name...",
                        modifier = Modifier,
                        prefixStatus = false,
                        KeyboardType.Text
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CommonTextField(
                        value = textTechLastName,
                        isEditing = true,
                        placeholderName = "Last Name...",
                        modifier = Modifier.height(52.dp),
                        prefixStatus = false,
                        KeyboardType.Text
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.779f)
                            .verticalScroll(rememberScrollState())
                    ) {

                        val isCheckedBreakSystem by remember { mutableStateOf(false) }
                        val checkboxColor = if(isCheckedBreakSystem) Color(0xFF253555) else Color.White

                        val servicesList= ArrayList<CheckBoxDetailsModel>()
                        servicesList.add(CheckBoxDetailsModel("dasd","Break System Repair", false))





                        Text(text = "Expertise Technician", style = textStyle1, modifier = Modifier.padding(16.dp))

                        servicesList.forEach{service->

                            Spacer(modifier = Modifier.height(8.dp))

                            var isChecked by remember { mutableStateOf(service.getIsSelected()) }

                            Row {

                                Spacer(modifier = Modifier.width(8.dp))

                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = {
                                        if (!isChecked){
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

                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        CommonButton(btnName = "Cancel", modifier = Modifier, onClickButton = {})
                        CommonButton(btnName = "Save", modifier = Modifier, onClickButton = {})
                    }
                }


            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Footer(navController,navyStatus)
    }
}