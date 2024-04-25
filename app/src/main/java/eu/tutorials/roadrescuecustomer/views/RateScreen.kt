package eu.tutorials.roadrescuecustomer.views

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.models.ServiceRequest
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel

@Composable
fun RateScreen(
    request: ServiceRequest,
    serviceRequestViewModel: ServiceRequestViewModel,
    onRate: () -> Unit){
    val context = LocalContext.current

     val loading by serviceRequestViewModel.loading
     CircularProgressBar(isDisplayed = loading)


    LaunchedEffect(key1 = Unit) {
        serviceRequestViewModel.ratingLoading.collect { close ->
            if (close) {
                onRate()
            }
        }
    }

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))// Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Provide your feedback . . .",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                style = textStyle2,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))


            var one by remember {
                mutableStateOf(false)
            }
            var two by remember {
                mutableStateOf(false)
            }
            var three by remember {
                mutableStateOf(false)
            }
            var four by remember {
                mutableStateOf(false)
            }
            var five by remember {
                mutableStateOf(false)
            }

            var stars by remember {
                mutableIntStateOf(0)
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.wrapContentHeight()
            ) {
                Row(horizontalArrangement = Arrangement.SpaceAround) {

                    Image(
                        painter = painterResource(id = if (one) R.drawable.star_1_filled else R.drawable.star_1),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            one = true
                            two = false
                            three = false
                            four = false
                            five = false
                            stars = 1

                        }
                    )
                    Image(
                        painter = painterResource(id = if (two) R.drawable.star_1_filled else R.drawable.star_1),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            one = true
                            two = true
                            three = false
                            four = false
                            five = false
                            stars = 2
                        }
                    )
                    Image(
                        painter = painterResource(id = if (three) R.drawable.star_1_filled else R.drawable.star_1),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            one = true
                            two = true
                            three = true
                            four = false
                            five = false
                            stars = 3
                        }
                    )
                    Image(
                        painter = painterResource(id = if (four) R.drawable.star_1_filled else R.drawable.star_1),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            one = true
                            two = true
                            three = true
                            four = true
                            five = false
                            stars = 4
                        }
                    )
                    Image(
                        painter = painterResource(id = if (five) R.drawable.star_1_filled else R.drawable.star_1),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            one = true
                            two = true
                            three = true
                            four = true
                            five = true
                            stars = 5
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            CommonButton(
                btnName = "Submit",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                if(stars > 0) {
                    serviceRequestViewModel.rateOrSkip(context,stars, requestId = request.id.toInt())
                } else {
                    Toast.makeText(context, "Select a rating", Toast.LENGTH_SHORT)
                }
            }
            CommonButton(
                btnName = "Skip",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                serviceRequestViewModel.rateOrSkip(context, requestId = request.id.toInt())
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}