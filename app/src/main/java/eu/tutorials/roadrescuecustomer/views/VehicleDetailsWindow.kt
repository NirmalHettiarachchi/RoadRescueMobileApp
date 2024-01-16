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
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel

@Composable
fun VehicleDetailsWindow(
    serviceRequestViewModel: ServiceRequestViewModel,
    onDismiss: () -> Unit
) {

    var vehicleType by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var vehicleMake by remember { mutableStateOf("") }
    var vehicleModel by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        tonalElevation = 16.dp,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFDCE4EC)
            ),
        containerColor = Color(0xFFDCE4EC),
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
                val vehicleMakeList = listOf("Toyota", "Honda", "BMW", "Audi")
                val vehicleModelList = listOf("Axio", "Premio", "Allion", "Vezel", "Graze")

                vehicleType = dropDown("Vehicle Type", vehicleTypeList)
                fuelType = dropDown("Fuel Type", fuelTypeList)
                vehicleMake = dropDown("Vehicle Make", vehicleMakeList)
                vehicleModel = dropDown("Vehicle Model",  vehicleModelList)

                Spacer(modifier = Modifier.height(8.dp))
                CommonButton(btnName = "Save", modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    if(vehicleModel.isNotEmpty()) {
                        serviceRequestViewModel.vehicleType.value = vehicleType
                        serviceRequestViewModel.fuelType.value = fuelType
                        serviceRequestViewModel.vehicleMake.value = vehicleMake
                        serviceRequestViewModel.vehicleModel.value = vehicleModel
                        onDismiss()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

