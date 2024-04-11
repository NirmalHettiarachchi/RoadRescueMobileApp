package com.example.garage.views


import android.net.Uri
import android.os.Build


import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.garage.repository.SetupNavGraph
import com.example.garage.ui.theme.GarageTheme


class MainActivity : ComponentActivity()  {




    private var selectedImageUri by mutableStateOf<Uri?>(null)
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
        }

    lateinit var navController:NavController


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            GarageTheme {
                navController= rememberNavController()
                SetupNavGraph(navController = navController as NavHostController)

            }
        }

    }
}




