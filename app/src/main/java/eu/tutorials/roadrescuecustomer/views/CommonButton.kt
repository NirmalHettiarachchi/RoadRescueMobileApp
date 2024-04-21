package eu.tutorials.roadrescuecustomer.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun ButtonPrev() {
    CommonButton("Demo", Modifier) {

    }
}


@Composable
fun CommonButton(btnName: String, modifier: Modifier, onClickButton: () -> Unit) {
    Button(
        onClick = { onClickButton() },
        modifier = modifier.then(Modifier.padding(horizontal = 65.dp).fillMaxWidth()),
        border = BorderStroke(width = 2.dp, color = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253555))
    ) {
        Text(
            text = btnName,
            style = textStyle3
        )
    }
}