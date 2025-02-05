package com.nerodev.optimaltic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nerodev.optimaltic.ui.theme.NeuralTacTheme
import com.nerodev.optimaltic.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeuralTacTheme {
                MainScreen()
            }
        }
    }
}