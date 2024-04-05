package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FillDetailsButton(detailButtonName: String, onClickButton: () -> Unit) {
    Button(
        onClick = { onClickButton() },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp), modifier = Modifier
            .width(285.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = detailButtonName,
            color = Color(0xFF253555),
            style = textStyle3.copy(textAlign = TextAlign.Center)
        )
    }
}