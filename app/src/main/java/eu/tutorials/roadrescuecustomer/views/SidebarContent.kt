package eu.tutorials.roadrescuecustomer.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.util.AppPreferences

@Composable
fun SidebarContent(menuClicked:(isLogOut : Boolean)->Unit,navHostController: NavHostController,context: Context){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(Color(0xFF253555))
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(30.dp)
                        .clickable {
                            menuClicked(false)
                        },
                    contentDescription = "Localized description"
                )
                Divider()
                Spacer(modifier = Modifier
                    .background(Color.White)
                    .height(2.dp))
            }
            SidebarButton(buttonName = "Help", verticalPadding = 8) {
                navHostController.navigate("helpscreen")
                menuClicked(false)
            }
            SidebarButton(buttonName = "Settings", verticalPadding = 8) {
                navHostController.navigate("settingsscreen")
                menuClicked(false)
            }
        }
        SidebarButton(buttonName = "Log Out", verticalPadding = 16) {
            AppPreferences(context).clearAllPreferences()
            navHostController.navigate("loginscreen") {
                popUpTo("loginscreen") { inclusive = true }
            }
            menuClicked(true)
        }
    }
}

@Composable
fun SidebarButton(buttonName: String, verticalPadding: Int,onClick:()->Unit) {
    Button(
        onClick = {
            onClick()
        },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = verticalPadding.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = buttonName,
            color = Color(0xFF253555),
            style = textStyle3.copy(textAlign = TextAlign.Center)
        )
    }
}
