package com.example.garage.views

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.garage.R
import com.example.garage.models.Garage
import com.example.garage.repository.Screen
import com.example.garage.viewModels.GarageSharedViewModel
import com.example.garage.viewModels.MainViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException


@Composable
fun GarageProfileEdit(
    navController: NavHostController,
    navStatus: String,
    garageSharedViewModel: GarageSharedViewModel
) {

    val garageData= garageSharedViewModel.garage

    var garageName by remember { mutableStateOf(garageData?.garageName) }
    var ownerName by remember { mutableStateOf(garageData?.garageOwner) }
    var garageStatus by remember { mutableStateOf(garageData?.garageStatus) }
    var contactNumber by remember { mutableStateOf(garageData?.garageContactNumber)}
    var email by remember { mutableStateOf(garageData?.garageEmail) }

    var showExpertiseArias by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showResponseDialog by remember { mutableStateOf(false) }
    val showDialogSelectPic = remember { mutableStateOf(false) }
    var showSweetAlert by remember { mutableStateOf(false) }
    var selectedServices by remember { mutableStateOf(emptyList<String>()) }

    val viewModel= viewModel<MainViewModel>()
    val coroutineScope = rememberCoroutineScope()

    var status by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }
    var expertiseAriasList by remember { mutableStateOf("") }


    val context= LocalContext.current
    var img: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(),android.R.drawable.ic_menu_report_image)
    var bitmap= remember { mutableStateOf(img) }

    // image loader and picker

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
            bitmap.value= MediaStore.Images.Media.getBitmap(context.contentResolver,it)
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
//        bitmap.value=getSaveImage(context,garageData?.techProfileRef)
        Log.d("image",img.toString())

    }

    // load response message
    if (showResponseDialog){
        sweetAlertDialog(
            title = title,
            message = message,
            buttonOneName = buttonOneName,
            buttonTwoName = buttonTwoName,
            onConfirm = {
                showDialog=false
                navController.navigate(route = Screen.TechnicianList.route)
            }
        )
    }




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


                        Image(
                            bitmap=bitmap.value.asImageBitmap(),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Unspecified)
                                .clip(CircleShape)
                                .clickable { }
                                .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape),
                            contentDescription = "Garage Pitcher",
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

                Text(
                    text = "Garage Name",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp,0.dp,160.dp,0.dp),
                    style = textStyle2
                )

                Spacer(modifier = Modifier.height(8.dp))

                garageName?.let {
                    CommonTextField(
                        it, true, "Garage Name", Modifier.height(46.dp), false,
                        KeyboardType.Text)
                }

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Owner Name",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp,0.dp,168.dp,0.dp),
                    style = textStyle2
                )

                Spacer(modifier = Modifier.height(8.dp))

                ownerName?.let { CommonTextField(it, true, "Owner Name", Modifier.height(46.dp), false,KeyboardType.Text) }

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Garage Status",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp,0.dp,155.dp,0.dp),
                    style = textStyle2
                )

                Spacer(modifier = Modifier.height(8.dp))

                garageStatus?.let { CommonTextField(it, true, "Garage Status", Modifier.height(46.dp), false,KeyboardType.Text) }

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Contact number",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp,0.dp,140.dp,0.dp),
                    style = textStyle2
                )

                Spacer(modifier = Modifier.height(8.dp))

                contactNumber?.let { CommonTextField(it, false, "Contact number", Modifier.height(46.dp), false,KeyboardType.Number) }


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Email",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp,0.dp,200.dp,0.dp),
                    style = textStyle2
                )

                Spacer(modifier = Modifier.height(8.dp))

                email?.let { CommonTextField(it, true, "Email", Modifier.height(46.dp), false,KeyboardType.Email) }


                //-----------------------------------------------------------------


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

                            bitmap.value.let { tempBitMap ->
                                showDialog=false
                                val saveLocation=saveImage(context,tempBitMap,garageData?.garageId)
                                if (saveLocation!=null) {
                                    coroutineScope.launch {

                                        try {
                                            val garage=
                                                garageName?.let { ownerName?.let { it1 ->
                                                    contactNumber?.let { it2 ->
                                                        email?.let { it3 ->
                                                            garageData?.garageId?.let { it4 ->
                                                                Garage(
                                                                    it4, it,
                                                                    it1, it2, it3,saveLocation)
                                                            }
                                                        }
                                                    }
                                                } }
                                            if (garage != null) {
                                                viewModel.updateGarage(garage){responseObject ->

                                                    if (responseObject != null){
                                                        if (responseObject.status==200) {
                                                            title = "Updated"
                                                            message = responseObject.message.toString()
                                                            buttonOneName = "nul"
                                                            buttonTwoName = "null"
                                                            showResponseDialog = true

                                                            garageName=garageName?.trim()
                                                            ownerName=ownerName?.trim()
                                                            garageStatus=garageStatus?.trim()
                                                            contactNumber=contactNumber?.trim()
                                                            email=email?.trim()

                                                            navController.navigate(route = Screen.GarageProfile.route)

                                                        } else if (responseObject.status == 500) {
                                                            title = "Failed"
                                                            message =
                                                                responseObject.message.toString()
                                                            buttonOneName = "null"
                                                            buttonTwoName = "null"
//                                                                showProgressBar.value=false
                                                            showResponseDialog = true
                                                        } else {
                                                            title = "Failed"
                                                            message = responseObject.toString()
                                                            buttonOneName = "null"
                                                            buttonTwoName = "null"
//                                                                    showProgressBar.value=false
                                                            showResponseDialog= true
                                                        }
                                                    }

                                                }
                                            }
                                        }catch (e: SocketTimeoutException){
                                            message= e.message.toString()
                                            buttonOneName= "null"
                                            buttonTwoName="null"
                                            showResponseDialog=true
                                        }catch (e:Exception) {
                                            message= e.message.toString()
                                            buttonOneName= "null"
                                            buttonTwoName="null"
                                            showResponseDialog=true
                                        }


                                    }
                                }
                            }
                        })

                    }


                }
            }
        )
    }

    // image loader

    if (showDialogSelectPic.value) {
        Dialog(
            onDismissRequest = { showDialogSelectPic.value = false },
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
                                    showDialogSelectPic.value = false
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
                                    showDialogSelectPic.value = false
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
}






