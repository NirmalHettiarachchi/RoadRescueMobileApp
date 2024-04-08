package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
    var vehicleTypeId by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var fuelTypeId by remember { mutableStateOf("") }
    var vehicleMake by remember { mutableStateOf("") }
    var vehicleMakeId by remember { mutableStateOf("") }
    var vehicleModel by remember { mutableStateOf("") }
    var vehicleModelId by remember { mutableStateOf("") }

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
                    text = "Fill your vehicle details here...",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))

                //----------------------------------------------------
                DisposableEffect(key1 = Unit) {
                    serviceRequestViewModel.fetchVehicleTypes()
                    serviceRequestViewModel.fetchFuelTypes()
                    serviceRequestViewModel.fetchVehicleMakes()
                    serviceRequestViewModel.fetchVehicleModels()
                    onDispose { }
                }
                val vehicleTypeList by serviceRequestViewModel.vehicleTypes
                val fuelTypeList by serviceRequestViewModel.fuelTypes
                val vehicleMakeList by serviceRequestViewModel.vehicleMakes
                val vehicleModelList by serviceRequestViewModel.vehicleModels

                val (getVehicleType, getVehicleTypeId) = dropDownVehicleType(
                    "Vehicle Type",
                    vehicleTypeList
                )
                vehicleType = getVehicleType
                vehicleTypeId = getVehicleTypeId
                val (getFuelType, getFuelTypeId) = dropDownFuel("Fuel Type", fuelTypeList)
                fuelType = getFuelType
                fuelTypeId = getFuelTypeId
                val (getVehicleMake, getVehicleMakeId) = dropDownVehicleMake(
                    "Vehicle Make",
                    vehicleMakeList
                )
                vehicleMake = getVehicleMake
                vehicleMakeId = getVehicleMakeId
                val (getVehicleModel, getVehicleModelId) = dropDownVehicleModel(
                    "Vehicle Model",
                    vehicleModelList.filter { it.vehicleModel.contains(vehicleMake) && it.vehicleModel.contains(fuelType) })
                vehicleModel = getVehicleModel
                vehicleModelId = getVehicleModelId

                Spacer(modifier = Modifier.height(8.dp))
                CommonButton(
                    btnName = "Save",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    if (vehicleModel.isNotEmpty()) {
                        serviceRequestViewModel.vehicleType.value.vehicleType = vehicleType
                        serviceRequestViewModel.vehicleType.value.id = getVehicleTypeId
                        serviceRequestViewModel.fuelType.value.id = getFuelTypeId
                        serviceRequestViewModel.fuelType.value.fuelType = fuelType
                        serviceRequestViewModel.vehicleMake.value.id = getVehicleMakeId
                        serviceRequestViewModel.vehicleMake.value.vehicleMake = vehicleMake
                        serviceRequestViewModel.vehicleModel.value.id = getVehicleModelId
                        serviceRequestViewModel.vehicleModel.value.vehicleModel = vehicleModel
                        onDismiss()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

