package eu.tutorials.roadrescuecustomer.views

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.R

@Composable
fun Footer(
    navigationToDashboardScreen: () -> Unit,
    navigationToProfileScreen: () -> Unit,
    navigationToTrackLocationScreen: () -> Unit,
    navigationToActivitiesScreen: () -> Unit,
    navigationToInstructionsScreen: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
            .background(color = Color(0xFF253555))
    ) {
        AnimatedIcon(painterId = R.drawable.book_fill, onClick = navigationToActivitiesScreen)
        AnimatedIcon(painterId = R.drawable.compass_fill, onClick = navigationToTrackLocationScreen)
        AnimatedIcon(painterId = R.drawable.home, onClick = navigationToDashboardScreen)
        AnimatedIcon(painterId = R.drawable.chat_fill, onClick = navigationToInstructionsScreen)
        AnimatedIcon(painterId = R.drawable.user_fill, onClick = navigationToProfileScreen)
//        Icon(
//            painter = painterResource(id = R.drawable.compass_fill),
//            modifier = Modifier
//                .padding(16.dp)
//                .size(45.dp)
//                .clickable{ navigationToTrackLocationScreen() },
//            tint = Color.Unspecified, contentDescription = null
//        )
//        Icon(
//            painter = painterResource(id = R.drawable.home),
//            modifier = Modifier
//                .padding(16.dp)
//                .size(45.dp)
//                .clickable{ navigationToDashboardScreen() },
//            tint = Color.Unspecified, contentDescription = null
//        )
//        Icon(
//            painter = painterResource(id = R.drawable.chat_fill),
//            modifier = Modifier
//                .padding(16.dp)
//                .size(45.dp)
//                .clickable{},
//            tint = Color.Unspecified, contentDescription = null
//        )
//        Icon(
//            painter = painterResource(id = R.drawable.user_fill),
//            modifier = Modifier
//                .padding(8.dp)
//                .size(40.dp)
//                .clickable{ navigationToProfileScreen() },
//            tint = Color.Unspecified, contentDescription = null
//        )
//

    }
}

@Composable
fun AnimatedIcon(
    painterId: Int,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale = animateFloatAsState(
        targetValue = if (isPressed) 0.75f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Icon(
        painter = painterResource(id = painterId),
        contentDescription = null,
        modifier = Modifier
            .padding(16.dp)
            .size(40.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .clickable(
                onClick = {
                    isPressed = true
                    onClick()
                }
            )
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}
