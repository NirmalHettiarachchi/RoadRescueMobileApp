package eu.tutorials.roadrescuecustomer.views

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.viewmodels.CurrentStateViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.LoginViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ProfileViewModel
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    loginViewModel: LoginViewModel,
    mainActivity: MainActivity,
    profileViewModel: ProfileViewModel,
    navHostController: NavHostController,
    currentStateViewModel: CurrentStateViewModel,
    serviceRequestViewModel: ServiceRequestViewModel
)
{
    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column{
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Settings",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle1
            )
            SettingsBox(loginViewModel, mainActivity, profileViewModel, navHostController, currentStateViewModel, serviceRequestViewModel)
        }
    }
}

@Composable
fun SettingsBox(loginViewModel: LoginViewModel, mainActivity: MainActivity, profileViewModel: ProfileViewModel, navHostController: NavHostController,
                currentStateViewModel: CurrentStateViewModel,
                serviceRequestViewModel: ServiceRequestViewModel) {

    var showChangePhoneNumWindow by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White), shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3)) // Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            FillDetailsButton(detailButtonName = "Change Phone Number") {
                showChangePhoneNumWindow = true
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if(showChangePhoneNumWindow) {
        ChangePhoneNumWindow (loginViewModel, mainActivity, profileViewModel, navHostController, currentStateViewModel, serviceRequestViewModel){
            showChangePhoneNumWindow = false
        }
    }
}