package com.example.garage.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun circularIndicatorProgressBar(
) {


    AlertDialog(
        onDismissRequest = {},
        modifier = Modifier.fillMaxHeight(0.25f).fillMaxWidth(0.7f),
        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(80.dp).height(60.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Black,
                )
            }

        },
        containerColor = Color(0x43F0E9E9),
        confirmButton = { /*TODO*/ }
    )


}