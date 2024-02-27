package com.example.garage.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun sweetAlertDialog(
    title: String,
    message: String?,
    buttonOneName: String?,
    buttonTwoName:String?,
    onConfirm: () -> Unit
) {
    if (buttonOneName.equals("null") && buttonTwoName.equals("null")){
        AlertDialog(
            onDismissRequest = onConfirm,
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = title,
                        style = textStyle5,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    if (message != null) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }else{
        AlertDialog(
            onDismissRequest = { /* Nothing to do here */ },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = title,
                        style = textStyle5,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    if (message != null) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            },
            confirmButton = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    if (buttonOneName.isNullOrEmpty() || buttonTwoName.isNullOrEmpty()){
                        Button(
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(Color.Gray)
                        ) {
                            if (buttonOneName != null) {
                                Text(text = buttonOneName)
                            }
                        }
                    }else{
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = buttonOneName)
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Button(
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = buttonTwoName)
                        }
                    }


                }
            }
        )
    }
}