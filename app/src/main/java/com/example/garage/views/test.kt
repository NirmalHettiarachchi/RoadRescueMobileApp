package com.example.garage.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


@Composable
fun GridWithTwoRows() {
    // Number of columns in the grid
    val numberOfColumns = 3

    // Data for the items in the grid
    val gridItems = (1 .. 20).toList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ){
        items(gridItems.size){
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "${gridItems}")
            }
        }
    }
}
