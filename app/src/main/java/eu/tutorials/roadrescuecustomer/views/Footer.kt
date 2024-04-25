package eu.tutorials.roadrescuecustomer.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.tutorials.roadrescuecustomer.R
import eu.tutorials.roadrescuecustomer.viewmodels.NavigationViewModel

@Composable
fun Footer(
    navigationToDashboardScreen: () -> Unit,
    navigationToProfileScreen: () -> Unit,
    navigationToTrackLocationScreen: () -> Unit,
    navigationToActivitiesScreen: () -> Unit,
    navigationToInstructionsScreen: () -> Unit,
    navigationViewModel: NavigationViewModel
) {

    val selectedIcon = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(key1 = true) {
        navigationViewModel.selectedFooterIcon.observeForever { icon ->
            selectedIcon.value = icon
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
            .background(color = Color(0xFF253555))
    ) {
        AnimatedIcon(
            selectedPainterId = R.drawable.book_fill,
            unselectedPainterId = R.drawable.book_un,
            isSelected = selectedIcon.value == R.drawable.book_fill,
            onClick = {
            navigationToActivitiesScreen()
            navigationViewModel.selectFooterIcon(R.drawable.book_fill)
        })
        AnimatedIcon(
            selectedPainterId = R.drawable.compass_fill,
            unselectedPainterId = R.drawable.compass_un,
            isSelected = selectedIcon.value == R.drawable.compass_fill, onClick = {
            navigationToTrackLocationScreen()
            navigationViewModel.selectFooterIcon(R.drawable.compass_fill)
        })
        AnimatedIcon(
            selectedPainterId = R.drawable.home,
            unselectedPainterId = R.drawable.home_un,
            isSelected = selectedIcon.value == R.drawable.home,
            onClick = {
            navigationToDashboardScreen()
            navigationViewModel.selectFooterIcon(R.drawable.home)
        })
        AnimatedIcon(
            selectedPainterId = R.drawable.chat_fill,
            unselectedPainterId = R.drawable.chat_un,
            isSelected = selectedIcon.value == R.drawable.chat_fill,
            onClick = {
            navigationToInstructionsScreen()
            navigationViewModel.selectFooterIcon(R.drawable.chat_fill)
        })
        AnimatedIcon(
            selectedPainterId = R.drawable.user_fill,
            unselectedPainterId = R.drawable.user_un,
            isSelected = selectedIcon.value == R.drawable.user_fill,
            onClick = {
            navigationToProfileScreen()
            navigationViewModel.selectFooterIcon(R.drawable.user_fill)
        })
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
    selectedPainterId: Int,
    unselectedPainterId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
//    var isPressed by remember { mutableStateOf(false) }

    val selectedPainter = remember(selectedPainterId) { selectedPainterId }
    val unselectedPainter = remember(unselectedPainterId) { unselectedPainterId }

    val iconPainter = if (isSelected) painterResource(id = selectedPainter) else painterResource(id = unselectedPainter)

//    val scale = animateFloatAsState(
//        targetValue = if (isPressed) 0.75f else 1f,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ), label = ""
//    )

    val animationSpec = tween<Color>(durationMillis = 300, easing = FastOutSlowInEasing)

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF80BAF2) else Color.White,
        animationSpec = animationSpec,
        label = ""
    )
    //icon color - 0xFFF4C9FF
    Icon(
        painter = iconPainter,
        contentDescription = null,
        tint = iconColor,
        modifier = Modifier
            .padding(16.dp)
            .size(40.dp)
//            .graphicsLayer {
//                scaleX = scale.value
//                scaleY = scale.value
//            }
            .clickable(
                onClick = {
//                    isPressed = true
                    onClick()
                }
            )
    )

//    LaunchedEffect(isPressed) {
//        if (isPressed) {
//            kotlinx.coroutines.delay(100)
//            isPressed = false
//        }
//    }
}
