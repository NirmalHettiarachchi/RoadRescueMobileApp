package com.example.garage.views


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.garage.R
import com.example.garage.models.Garage
import com.example.garage.repository.GarageCommonDetails
import com.example.garage.repository.Screen
import com.example.garage.viewModels.GarageProfileViewModel
import com.example.garage.viewModels.GarageSharedViewModel
import com.example.garage.viewModels.MainViewModel
import org.json.JSONArray


@Composable
fun GarageProfile(
    garageId: String?,
    garageName: String?,
    garageContactNumber: String?,
    garageStatus: String?,
    garageRating: String?,
    garageType: String?,
    garageEmail: String?,
    garageOwner: String?,
    navController: NavController,
    navyStatus: String,
    garageSharedViewModel: GarageSharedViewModel,
) {

    var garageRatings by remember { mutableStateOf("") }


    if ((garageRatings.toIntOrNull() ?: 0) == 0){

        garageRatings="not insert the rating"
    }else{
        if (garageRating != null) {
            garageRatings=garageRating
        }
    }



    val processGarage= Garage(
                garageId!!,
                garageName!!,
                garageOwner!!,garageContactNumber!!,garageStatus!!,garageEmail!!,garageRating?.toFloat()!!,garageType!!
    )



    val viewModel= viewModel<MainViewModel>()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val listOfServices = ArrayList<GarageProfileViewModel>()
    val coroutineScope = rememberCoroutineScope()
    var expertiseAriasList by remember { mutableStateOf("") }
    var showExpertiseArias by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    var status by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var buttonOneName by remember { mutableStateOf("") }
    var buttonTwoName by remember { mutableStateOf("") }

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
                buttonOneName="null"
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
            buttonOneName="null"
            buttonTwoName="null"
            showDialog.value=true
            Log.d("response null","null")
        }
    }



    if (showExpertiseArias) {
        val jsonArray = JSONArray(expertiseAriasList)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val techExpertiseId = jsonObject.getString("expertiseId")
            val techExpertise = jsonObject.getString("expertise")

            listOfServices.add(
                GarageProfileViewModel(
                    R.drawable.new_servises,
                    techExpertise
                )
            )
        }
    }


    Column(
        modifier = defaultBackground,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Header(menuClicked = {})

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = cardDefaultModifier,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)),
            border = BorderStroke(width = 2.dp, Color.White),
        ) {
            Column {

                // create profile pic and garage name

                val context = LocalContext.current

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(8.dp))

                    // set profile pitcher
                    Card(
                        shape = CircleShape,
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight(0.17f)
                            .fillMaxWidth()
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Unspecified)
                                .clip(CircleShape)
                                .clickable { }
                                .border(BorderStroke(2.dp, Color.Unspecified), shape = CircleShape),
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
                            .size(16.dp)
                            .align(Alignment.Bottom)
                            .background(Color(0xFF253555), shape = RoundedCornerShape(4.dp))
                            .clickable {
                                val garageData = GarageCommonDetails(
                                    processGarage.getGarageId(),
                                    processGarage.getGarageName(),
                                    processGarage.getGarageContactNumber(),
                                    processGarage.getGarageStatus(),
                                    processGarage.getGarageEmail(),
                                    processGarage
                                        .getGarageRating()
                                        .toString(),
                                    processGarage.getGarageType(),
                                    processGarage.getOwnerName()

                                )
                                garageSharedViewModel.garageCommonDetails(garageData)
                                navController.navigate(route = Screen.GarageProfileEdit.route)
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(0.dp, 14.dp, 2.dp)
                    ) {

                        Column {
                            Text(
                                text = buildAnnotatedString {

                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 50.sp
                                        )
                                    ) {
                                        append(garageName?.get(0).toString())
                                    }

                                    append(garageName?.substring(1))

                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 50.sp
                                        )
                                    ) {
                                        append(" G")
                                    }
                                    append("arage")

                                },
                                color = Color(0xFF253555),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 30.sp,
                                fontFamily = FontFamily.Serif,
                                textAlign = TextAlign.Justify,
                                maxLines = 3,
                                lineHeight = 50.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = garageRatings,
                                color = Color(0xB3000000),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                            )


                            if (garageRating!=null){
                                Text(
                                    text = garageOwner,
                                    color = Color(0xB3000000),
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Create contact row

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.09f)
                ) {

                    Box(
                        modifier = Modifier
                            .weight(0.99f)
                            .fillMaxSize()
                            .clickable {
                                val intent = Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.parse("tel:${garageContactNumber}")
                                )
                                context.startActivity(intent)
                            },
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call icon",
                            modifier = Modifier.padding(10.dp, 0.dp),
                            tint = Color.White
                        )

                        if (garageContactNumber != null) {
                            Text(
                                text = garageContactNumber,
                                color = Color(0xB3000000),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(36.dp, 0.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .clickable {
                                garageEmail?.let { email ->
                                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("mailto:$email")
                                    }
                                    if (intent.resolveActivity(context.packageManager) != null) {
                                        startActivity(context, intent, null)
                                    }
                                }

                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email icon",
                            modifier = Modifier.padding(8.dp, 0.dp),
                            tint = Color.White
                        )

                        if (garageEmail != null) {
                            Text(
                                text = garageEmail,
                                color = Color(0xB3000000),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(36.dp, 0.dp),
                                maxLines = 1
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Create icons list

                val listOfArray = ArrayList<GarageProfileViewModel>()

                listOfArray.add(GarageProfileViewModel(R.drawable.technicians, "Technician","TechnicianList"))
                listOfArray.add(GarageProfileViewModel(R.drawable.did_job, "Other"," "))



                // load icons

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f)
                ) {

                    listOfArray.forEach { icon ->

                        Spacer(modifier = Modifier.width(32.dp))

                        Box(
                            modifier = Modifier
                                .size(109.dp)
                                .background(Color.Unspecified)
                                .padding(8.dp)
                                .fillMaxSize()
                                .border(
                                    BorderStroke(2.dp, Color.Black),
                                    shape = RoundedCornerShape(20)
                                )
                                .clickable {
                                    navController.navigate(route = Screen.TechnicianList.route)
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = icon.getIconPath()),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            )
                        }
                    }
                }

                // load names

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.12f)
                ) {

                    listOfArray.forEach { icon ->
                        Spacer(modifier = Modifier.width(44.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = icon.getIconName(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                                modifier = Modifier
                            )
                        }
                    }

                }

                Text(
                    text = "Services",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
                    textDecoration = TextDecoration.Underline
                )


                // create a services list






                // import services

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    listOfServices.forEach { services ->

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.2f)
                                .padding(16.dp, 0.dp, 0.dp, 0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = services.getIconPath()),
                                contentDescription = services.getIconName(),
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(32.dp),

                                )


                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = services.getIconName(),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

            }

        }

        Spacer(modifier = Modifier.height(26.dp))

        Footer(navController, navyStatus)
    }
}