package com.example.garage.views

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.garage.R
import com.example.garage.models.CheckBoxDetailsModel
import com.example.garage.repository.Screen
import com.example.garage.viewModels.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.json.JSONArray
import java.io.ByteArrayOutputStream

@Composable
fun EditTechnician(
    navController: NavController, navyStatus:String
){

    var textTechFirstName by remember { mutableStateOf("") }
    var textTechLastName by remember { mutableStateOf("") }

    val viewModel= viewModel<MainViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    val showDialogSelectPic = remember { mutableStateOf(false) }
    var showExpertiseArias by remember { mutableStateOf(false) }
    var selectedServices by remember { mutableStateOf(emptyList<String>()) }
    var isUploading by remember{mutableStateOf(false)}

    var status by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }
    var expertiseAriasList by remember { mutableStateOf("") }


    val context= LocalContext.current
    val img:Bitmap=BitmapFactory.decodeResource(Resources.getSystem(),android.R.drawable.ic_menu_report_image)
    val bitmap= remember { mutableStateOf(img) }


    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            if (it!=null){
                bitmap.value=it
            }
        })

    var launcherImage= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        if (Build.VERSION.SDK_INT<28){
            bitmap.value=MediaStore.Images.Media.getBitmap(context.contentResolver,it)
        }else{
            val source=it?.let { tempIt->
                ImageDecoder.createSource(context.contentResolver,tempIt)
            }
            bitmap.value=source?.let {tempIt->
                ImageDecoder.decodeBitmap(tempIt)
            }!!
        }
    }


    LaunchedEffect(Unit) {
        val response=loadExpertiseArias(viewModel,coroutineScope)
        if (response != null) {
            if(response?.status==200){

                expertiseAriasList= response.data!!.toString()
                showExpertiseArias=true

            }else if(response.status==400){
                title=response.status.toString()
                message= response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true

            }else if(response.status==404){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true

            }else if(response.status==500){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true
            }else if(response.status==508){
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="null"
                buttonTwoName="null"
                showDialog.value=true
            }else{
                title=response.status.toString()
                message=response.message.toString()
                buttonOneName="Ok"
                buttonTwoName="null"
                showDialog.value=true
            }
        }else{
            status=401
            message="Cannot call the sever"
            buttonOneName="Ok"
            buttonTwoName="null"
            showDialog.value=true

        }
    }





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


                        Image(
                            bitmap=bitmap.value.asImageBitmap(),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Unspecified)
                                .clip(CircleShape)
                                .clickable { }
                                .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape),
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
                                showDialogSelectPic.value = true
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


                        if (showExpertiseArias) {
                            val jsonArray = JSONArray(expertiseAriasList)
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val techExpertiseId = jsonObject.getString("expertiseId")
                                val techExpertise = jsonObject.getString("expertise")
                                servicesList.add(CheckBoxDetailsModel(techExpertiseId,techExpertise, false))
                            }
                        }

                        Text(text = "Expertise Technician", style = textStyle1, modifier = Modifier.padding(16.dp))

                        servicesList.forEach{service->

                            Spacer(modifier = Modifier.height(8.dp))


                            Row {

                                Spacer(modifier = Modifier.width(8.dp))

                                Checkbox(
                                    checked = selectedServices.contains(service.getCheckBoxName()),
                                    onCheckedChange = { isChecked ->
                                        selectedServices = if (isChecked) {
                                            selectedServices + service.getCheckBoxName()+"-"+service.getCheckBoxId()
                                        } else {
                                            selectedServices - service.getCheckBoxName()+"-"+service.getCheckBoxId()
                                        }
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

                        // technician update
                        CommonButton(btnName = "Save", modifier = Modifier, onClickButton = {
                           bitmap.value.let {tempBitmap ->
                               uploadImageToFirebase(tempBitmap,context as ComponentActivity){success->
                                   if (success){
                                       Toast.makeText(context,"Upload Successfully.",Toast.LENGTH_SHORT).show()
                                   }else{
                                       Toast.makeText(context,"Failed to Upload.",Toast.LENGTH_SHORT).show()
                                   }
                               }

                           }
                        })
                    }
                }


            }
        }

        //-------
        if (showDialogSelectPic.value) {
            Dialog(
                onDismissRequest = { /*TODO*/ },
                content = {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .width(300.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF253555))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White)
                                    .clickable {
                                        launcher.launch()
                                        showDialogSelectPic.value=false
                                    }
                            )
                            Text(
                                text = "Camera",
                                style = textStyle4
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.2f))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_image_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White)
                                    .clickable {
                                        launcherImage.launch("image/*")
                                        showDialogSelectPic.value=false
                                    }
                            )
                            Text(
                                text = "Gallery",
                                style = textStyle4
                            )
                        }

                    }
                }
            )

        }

        // load response message
        if (showDialog.value){
            sweetAlertDialog(
                title = title,
                message = message,
                buttonOneName = buttonOneName,
                buttonTwoName = buttonTwoName,
                onConfirm = {
                    showDialog.value=false
                    navController.navigate(route = Screen.TechnicianList.route)
                }
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        Footer(navController,navyStatus)
    }
}

fun uploadImageToFirebase(bitmap: Bitmap,context:ComponentActivity,callback:(Boolean)-> Unit) {
    val storageRef=Firebase.storage.reference
    val imageRef= storageRef.child("techniciansProfilePic/${bitmap}")

    val bass=ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bass)
    val imageData=bass.toByteArray()

    imageRef.putBytes(imageData).addOnSuccessListener {
        callback(true)
    }.addOnFailureListener{
        callback(false)
    }
}

