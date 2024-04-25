package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.viewmodels.TipViewModel
import kotlin.random.Random

@Composable
fun InstructionsScreen(tipViewModel: TipViewModel) {

    var loading by remember {
        mutableStateOf(tipViewModel.loading)
    }
    CircularProgressBar(isDisplayed = loading.value)

    Column(
        backgroundModifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tips & Guidelines",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = textStyle1
            )
            QuickTip(tipViewModel)
            Card(
                modifier = cardModifier,
                border = BorderStroke(width = 2.dp, Color.White),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))// Apply shadow to the outer Box
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    contentAlignment = Alignment.Center, // Center content within the Box
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Quick guidelines",
                        style = textStyle2,
                        textAlign = TextAlign.Center
                    )
                }
                WithInstructionScreen("Your phone should be connected to the internet.")
                WithInstructionScreen("Complete all required details to request the service.")
                WithInstructionScreen("If the issue is not listed, select 'Other' from the options. The same applies to the vehicle.")
                WithInstructionScreen("If a garage does not accept your request within three minutes, the system will automatically cancel it.")
                WithInstructionScreen("To contact the service provider, use the 'Contact Service Provider' option on the track location screen.")
                WithInstructionScreen("Make the payment in cash or by card after receiving the service.")
                WithInstructionScreen("If you experience any issues, please contact support.")
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithInstructionScreen(instruction: String) {
    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {}
    ) {
        Box(
            contentAlignment = Alignment.Center, // Center content within the Box
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = instruction,
                style = textStyle4,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun QuickTip(tipViewModel: TipViewModel) {
    val tipId = getRandomInt(1, 18)
    LaunchedEffect(key1 = true) {
        tipViewModel.fetchTip(tipId.toString())
    }
    val tip = tipViewModel.tips.collectAsState().value.firstOrNull()?.tip
    if (!tip.isNullOrEmpty()) {
        QuickTipBox(quickTip = tip)
    } else {
        QuickTipBox("No tips available at the moment. Check back later.")
    }
}

@Composable
fun QuickTipBox(quickTip: String) {
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
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                contentAlignment = Alignment.Center, // Center content within the Box
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = quickTip,
                    style = textStyle5,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun getRandomInt(from: Int, to: Int): Int {
    return Random.nextInt(from, to + 1)
}
