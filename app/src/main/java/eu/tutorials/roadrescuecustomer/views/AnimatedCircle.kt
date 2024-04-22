package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.R
import kotlinx.coroutines.delay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun AnimatedCircle(isDisplayed: Boolean) {
    if (isDisplayed) {
        var rotationState by remember { mutableStateOf(0f) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(20)
                rotationState += 5f
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.loading_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .rotate(rotationState),
                colorFilter = ColorFilter.tint(Color(0xFF253555))
            )
        }
    }
}
