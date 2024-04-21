package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.models.FuelType
import eu.tutorials.roadrescuecustomer.models.Issues
import eu.tutorials.roadrescuecustomer.models.VehicleMake
import eu.tutorials.roadrescuecustomer.models.VehicleModel
import eu.tutorials.roadrescuecustomer.models.VehicleType

@Composable
fun dropDownFuel(dropDownText: String, dropDownListItems: List<FuelType>): Pair<String, String> {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(dropDownText) }
    var selectedId by remember { mutableStateOf("") }

    Box {
        Button(
            onClick = { isExpanded = true },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedValue,
                    color = Color(0xFF253555)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color(0xFF253555)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(270.dp)
                .background(Color.White)
        ) {
            dropDownListItems.forEachIndexed { index, dropDownListItem ->
                DropdownMenuItem(
                    text = { Text(text = dropDownListItem.fuelType, color = Color(0xFF253555)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onClick = {
                        isExpanded = false
                        selectedValue = dropDownListItem.fuelType
                        selectedId = dropDownListItem.id
                    }
                )
                if (index < dropDownListItems.size - 1) {
                    Divider()
                }
            }
        }
    }
    return Pair(selectedValue, selectedId)
}

@Composable
fun dropDownVehicleType(
    dropDownText: String,
    dropDownListItems: List<VehicleType>
): Pair<String, String> {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(dropDownText) }
    var selectedId by remember { mutableStateOf(dropDownText) }

    Box {
        Button(
            onClick = { isExpanded = true },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedValue,
                    color = Color(0xFF253555)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color(0xFF253555)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(270.dp)
                .background(Color.White)
        ) {
            dropDownListItems.forEachIndexed { index, dropDownListItem ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = dropDownListItem.vehicleType,
                            color = Color(0xFF253555)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onClick = {
                        isExpanded = false
                        selectedValue = dropDownListItem.vehicleType
                        selectedId = dropDownListItem.id
                    }
                )
                if (index < dropDownListItems.size - 1) {
                    Divider()
                }
            }
        }
    }
    return Pair(selectedValue, selectedId)
}

@Composable
fun dropDownVehicleMake(
    dropDownText: String,
    dropDownListItems: List<VehicleMake>
): Pair<String, String> {

    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(dropDownText) }
    var selectedId by remember { mutableStateOf(dropDownText) }

    Box {
        Button(
            onClick = { isExpanded = true },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedValue,
                    color = Color(0xFF253555)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color(0xFF253555)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(270.dp)
                .background(Color.White)
        ) {
            dropDownListItems.forEachIndexed { index, dropDownListItem ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = dropDownListItem.vehicleMake,
                            color = Color(0xFF253555)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onClick = {
                        isExpanded = false
                        selectedValue = dropDownListItem.vehicleMake
                        selectedId = dropDownListItem.id
                    }
                )
                if (index < dropDownListItems.size - 1) {
                    Divider()
                }
            }
        }
    }
    return Pair(selectedValue, selectedId)
}

@Composable
fun dropDownIssues(
    dropDownText: String,
    dropDownListItems: List<Issues>
): Issues {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf<Issues>(Issues("", "", "")) }
    var selectedId by remember { mutableStateOf("") }

    Box {

        Button(
            onClick = { isExpanded = true },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (selectedValue.category.isNotEmpty()) selectedValue.category else "Issue",
                    color = Color(0xFF253555)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color(0xFF253555)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(270.dp)
                .background(Color.White)
        ) {
            dropDownListItems.forEachIndexed { index, dropDownListItem ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = dropDownListItem.category,
                            color = Color(0xFF253555)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onClick = {
                        isExpanded = false
                        selectedValue = dropDownListItem
                    }
                )
                if (index < dropDownListItems.size - 1) {
                    Divider()
                }
            }
        }
    }
    return selectedValue
}

@Composable
fun dropDownVehicleModel(
    dropDownText: String,
    dropDownListItems: List<VehicleModel>
): Pair<String, String> {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(dropDownText) }
    var selectedId by remember { mutableStateOf("") }

    Box {
        Button(
            onClick = { isExpanded = true },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedValue,
                    color = Color(0xFF253555)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color(0xFF253555)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(270.dp)
                .background(Color.White)
        ) {
            dropDownListItems.forEachIndexed { index, dropDownListItem ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = dropDownListItem.vehicleModel,
                            color = Color(0xFF253555)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onClick = {
                        isExpanded = false
                        selectedValue = dropDownListItem.vehicleModel
                        selectedId = dropDownListItem.id
                    }
                )
                if (index < dropDownListItems.size - 1) {
                    Divider()
                }
            }
        }
    }
    return Pair(selectedValue, selectedId)
}

@Composable
fun dropDownCommon(dropDownText: String, dropDownListItems: List<String>): String {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(dropDownText) }

    Box {
        Button(
            onClick = { isExpanded = true },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedValue,
                    color = Color(0xFF253555)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Down",
                    tint = Color(0xFF253555)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(270.dp)
                .background(Color.White)
        ) {
            dropDownListItems.forEachIndexed { index, dropDownListItem ->
                DropdownMenuItem(
                    text = { Text(text = dropDownListItem, color = Color(0xFF253555)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onClick = {
                        isExpanded = false
                        selectedValue = dropDownListItem
                    }
                )
                if (index < dropDownListItems.size - 1) {
                    Divider()
                }
            }
        }
    }

    return if(selectedValue !in dropDownListItems) {
        ""
    } else {
        selectedValue
    }
}