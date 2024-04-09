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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.garage.R
import com.example.garage.models.CheckBoxDetailsModel
import com.example.garage.repository.Screen
import com.example.garage.viewModels.GarageSharedViewModel



@Composable
fun GarageProfileEdit(
    navController: NavHostController,
    navStatus: String,
    garageSharedViewModel: GarageSharedViewModel
) {

    val garageData= garageSharedViewModel.garage
    val partsOfName=garageData?.garageOwner?.split(" ")
    val textFirstName by remember { mutableStateOf(partsOfName!![0]) }
    val textLastName by remember { mutableStateOf(partsOfName!![1]) }
    val garageName by remember { mutableStateOf(garageData?.garageName) }
    val contactNumber by remember { mutableStateOf(garageData?.garageContactNumber)}
    val email by remember { mutableStateOf(garageData?.garageEmail) }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = defaultBackground,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Header(menuClicked = {})

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = cardDefaultModifier,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
            border = BorderStroke(width = 2.dp, Color.White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

                val photoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = {
                        selectedImageUri = it
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Card(
                        shape = CircleShape,
                        border = BorderStroke(width = 2.dp, color = Color.Unspecified),
                        modifier = Modifier
                            .padding(8.dp, 16.dp, 8.dp, 8.dp)
                            .fillMaxHeight(0.15f)
                            .fillMaxWidth(0.28f)
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Unspecified)
                                .clip(CircleShape)
                                .clickable { }
                                .border(
                                    BorderStroke(2.dp, Color.Unspecified),
                                    shape = CircleShape
                                ),
                            model = if (selectedImageUri == null) {
                                R.drawable.user_fill
                            } else {
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

                CommonTextField(textFirstName, true, "First Name", Modifier.height(46.dp), false,
                    KeyboardType.Text)

                Spacer(modifier = Modifier.height(8.dp))


                CommonTextField(textLastName, true, "Last Name", Modifier.height(46.dp), false,KeyboardType.Text)

                Spacer(modifier = Modifier.height(8.dp))


                garageName?.let { CommonTextField(it, true, "Garage Name", Modifier.height(46.dp), false,KeyboardType.Text) }

                Spacer(modifier = Modifier.height(8.dp))


                contactNumber?.let { CommonTextField(it, false, "Contact number", Modifier.height(46.dp), false,KeyboardType.Number) }


                Spacer(modifier = Modifier.height(8.dp))

                email?.let { CommonTextField(it, true, "Email", Modifier.height(46.dp), false,KeyboardType.Email) }


                //-----------------------------------------------------------------


                Text(
                    text = "Services",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(start = 8.dp)
                )



                val isCheckedBraekSysytem by remember { mutableStateOf(false) }
                var isCheckedOilChange by remember { mutableStateOf(false) }
                var isCheckedEngineRepair by remember { mutableStateOf(false) }
                var isCheckedTireRepair by remember { mutableStateOf(false) }

                val checkboxColor =
                    if (isCheckedBraekSysytem) Color(0xFF253555) else Color.White

                val servicesList = ArrayList<CheckBoxDetailsModel>()
                servicesList.add(CheckBoxDetailsModel("as","Break System Repair", false))
                servicesList.add(CheckBoxDetailsModel("as","Oil Change", false))
                servicesList.add(CheckBoxDetailsModel("as","Tire Replacement", false))




                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .padding(start = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(servicesList) { service ->


                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                            var isChecked by remember { mutableStateOf(service.getIsSelected()) }

                                Checkbox(
                                    checked = service.getIsSelected(),
                                    onCheckedChange = { newCheckState ->
                                        isChecked = newCheckState
                                    },
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
                                    modifier = Modifier.padding(start = 24.dp),
                                    fontFamily = fontFamily
                                )
                            }
                        }
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))

                    CommonButton(btnName = "Cancel", modifier = Modifier, onClickButton = {

                        navController.navigate(route = Screen.GarageDashboard.route)
                    })

                    Spacer(modifier = Modifier.width(16.dp))

                    CommonButton(btnName = "Save", modifier = Modifier, onClickButton = {showDialog=true})

                    Spacer(modifier = Modifier.width(8.dp))

                }

            }

        }

        Spacer(modifier = Modifier.height(25.dp))

        Footer(navController, navStatus)
    }



    if (showDialog){
        Dialog(
            onDismissRequest = {  },
            content = {
                Column (
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.2f)
                        .background(
                            Color(0xFFACB3C0),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Your details was updated.",style = textStyleDefault)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        CommonButton(btnName = "Cancel", modifier = Modifier, onClickButton = {showDialog=false})

                        CommonButton(btnName = "Yes", modifier = Modifier, onClickButton = {
                            showDialog=false
                            navController.navigate(route = Screen.GarageProfile.route)
                        })

                    }


                }
            }
        )
    }

}
