package com.nerodev.optimaltic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nerodev.optimaltic.core.ui.theme.NeuralTacTheme
import com.nerodev.optimaltic.presentation.screen.MainScreen
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeuralTacTheme {
                KoinContext{
                    MainScreen()
                }
            }
        }
    }
}