package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun dropDown(dropDownText: String, dropDownListItems: List<String>): String {
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