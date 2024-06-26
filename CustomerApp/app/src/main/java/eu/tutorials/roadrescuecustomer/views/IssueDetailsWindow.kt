package eu.tutorials.roadrescuecustomer.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel

@Composable
fun IssueDetailsWindow(
    serviceRequestViewModel: ServiceRequestViewModel,
    onDismiss: () -> Unit
) {

    var checkEngineIndicator by remember { mutableStateOf(false) }
    var batteryIndicator by remember { mutableStateOf(false) }
    var coolantTemperatureIndicator by remember { mutableStateOf(false) }
    var transmissionIndicator by remember { mutableStateOf(false) }
    var oilPressureWarningIndicator by remember { mutableStateOf(false) }
    var brakeSystemIndicator by remember { mutableStateOf(false) }
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
                    text = "Fill the issue details here...",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))
                DisposableEffect(key1 = Unit) {
                    serviceRequestViewModel.fetchIssues()
                    onDispose {

                    }
                }
                val issueList by serviceRequestViewModel.issues

                val selectedIssue = dropDownIssues("Issue", issueList)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Select the indicators (optional)",
                    style = textStyle2
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .width(220.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    checkEngineIndicator = toggle(initialState = checkEngineIndicator)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Engine",
                        style = textStyle2
                    )
                }

                Row(
                    modifier = Modifier
                        .width(220.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    batteryIndicator = toggle(initialState = batteryIndicator)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Battery",
                        style = textStyle2
                    )
                }

                Row(
                    modifier = Modifier
                        .width(220.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    coolantTemperatureIndicator = toggle(initialState = coolantTemperatureIndicator)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Coolant Temperature",
                        style = textStyle2,
                        maxLines = 1
                    )
                }

                Row(
                    modifier = Modifier
                        .width(220.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    transmissionIndicator = toggle(initialState = transmissionIndicator)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Transmission",
                        style = textStyle2
                    )
                }

                Row(
                    modifier = Modifier
                        .width(220.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    oilPressureWarningIndicator = toggle(initialState = oilPressureWarningIndicator)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Oil Pressure",
                        style = textStyle2
                    )
                }

                Row(
                    modifier = Modifier
                        .width(220.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    brakeSystemIndicator = toggle(initialState = brakeSystemIndicator)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Brake System",
                        style = textStyle2
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                CommonButton(btnName = "Save", modifier = Modifier) {
                    serviceRequestViewModel.issue.value = selectedIssue
                    serviceRequestViewModel.indicator1.value=checkEngineIndicator
                    serviceRequestViewModel.indicator2.value=batteryIndicator
                    serviceRequestViewModel.indicator3.value=coolantTemperatureIndicator
                    serviceRequestViewModel.indicator4.value=transmissionIndicator
                    serviceRequestViewModel.indicator5.value=oilPressureWarningIndicator
                    serviceRequestViewModel.indicator6.value=brakeSystemIndicator
                    if (serviceRequestViewModel.issue.value.id.isNotEmpty()) {
                        onDismiss()
                    } else {
                        Toast.makeText(context, "Select an issue", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
}

@Composable
fun toggle(initialState: Boolean): Boolean {
    var indicatorState by remember { mutableStateOf(initialState) }
    Switch(
        checked = indicatorState,
        onCheckedChange = {
            indicatorState = it
        }
    )
    return indicatorState
}