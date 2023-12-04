package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VehicleDetailsWindow(onDismiss: () -> Unit) {

    var vehicleType by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        tonalElevation = 16.dp,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFB6C7E3)
            ),
        containerColor = Color(0xFFB6C7E3),
        confirmButton = {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Fill you vehicle details here...",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))

                val vehicleTypeList = listOf("Car", "Van", "Lorry", "Bicycle")
                val fuelTypeList = listOf("Petrol", "Diesel", "Hybrid", "Electric")

                vehicleType = dropDown("Vehicle Type", vehicleTypeList)
                fuelType = dropDown("Fuel Type", fuelTypeList)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

