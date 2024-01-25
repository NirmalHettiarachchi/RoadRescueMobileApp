package com.example.garage.views

import android.net.Uri
import android.widget.ImageButton
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.garage.R

@Composable
fun EditTechnician(){

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

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
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            ) }
                    )

                }

                Column (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Footer()
    }
}