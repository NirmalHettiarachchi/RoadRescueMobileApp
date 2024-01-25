package com.example.garage.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.garage.R
import com.example.garage.viewModels.CheckBoxDetailsModel



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

                    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

                    var photoPickerLauncher= rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult ={
                            selectedImageUri=it
                        }
                    )

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
                            border = BorderStroke(width = 2.dp, color = Color.Unspecified),
                            modifier = Modifier
                                .padding(8.dp, 16.dp, 8.dp, 8.dp)
                                .fillMaxHeight(0.15f)
                                .fillMaxWidth(0.3f)
                        ) {

                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Unspecified)
                                    .clip(CircleShape).clickable {  }
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
                                    ) }
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    CommonTextField(textFirstName, true, "First Name",Modifier.weight(1f),false)

                    Spacer(modifier = Modifier.height(8.dp))


                    CommonTextField(textLastName, true, "Last Name",Modifier.weight(1f),false)

                    Spacer(modifier = Modifier.height(8.dp))


                    CommonTextField(garageName, true, "Garage Name",Modifier.weight(1f),false)

                    Spacer(modifier = Modifier.height(8.dp))


                        CommonTextField(contactNumber, false, "Contact number",Modifier.weight(1f),false)


                    Spacer(modifier = Modifier.height(8.dp))

                        CommonTextField(email, true, "Email",Modifier.weight(1f),false)


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

                                val servicesList= ArrayList<CheckBoxDetailsModel>()
                                servicesList.add(CheckBoxDetailsModel("Break System Repair", false))
                                servicesList.add(CheckBoxDetailsModel("Oil Change",false))
                                servicesList.add(CheckBoxDetailsModel("Tire Replacement",false))
                                servicesList.add(CheckBoxDetailsModel("Engine Repair",false))


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
