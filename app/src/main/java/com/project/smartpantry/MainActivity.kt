package com.project.smartpantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.project.smartpantry.navigation.SmartPantryApp
import com.project.smartpantry.ui.theme.SmartPantryTheme
import dagger.hilt.android.AndroidEntryPoint
//Compose screens hosted by this Activity can then obtain Hilt-created ViewModels
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartPantryTheme {
                SmartPantryApp()
            }
        }
    }
}
