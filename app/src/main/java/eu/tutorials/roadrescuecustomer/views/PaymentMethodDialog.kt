package eu.tutorials.roadrescuecustomer.views

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel

@Composable
fun PaymentMethodDialog(
    serviceRequestViewModel: ServiceRequestViewModel,
    onDismiss: () -> Unit
) {

    val context = LocalContext.current

    val loading = serviceRequestViewModel.loading.value

    CircularProgressBar(isDisplayed = loading)

    AlertDialog(
        onDismissRequest = { onDismiss() },
        tonalElevation = 16.dp,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFDCE4EC)
            )
            .verticalScroll(rememberScrollState()),
        containerColor = Color(0xFFDCE4EC),
        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Select Payment Method",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {  },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    border = BorderStroke(width = 2.dp, color = Color.White),
                    modifier = Modifier
                        .height(58.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
                ) {
                        Text(
                            text = "Cash",
                            style = textStyle2,
                        )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {  },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    border = BorderStroke(width = 2.dp, color = Color.White),
                    modifier = Modifier
                        .height(58.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
                ) {
                    Text(
                        text = "Card",
                        style = textStyle2,
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}