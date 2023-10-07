package com.samkt.filmio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.samkt.filmio.presentation.homeScreen.HomeScreen
import com.samkt.filmio.presentation.navigation.ApplicationHomeScreen
import com.samkt.filmio.ui.theme.FilmioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmioTheme {
                ApplicationHomeScreen()
            }
        }
    }
}
