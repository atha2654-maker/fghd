package com.vandemunconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vandemunconnect.ui.navigation.AppNavHost
import com.vandemunconnect.ui.theme.VandeMUNTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VandeMUNTheme {
                val systemUiController = rememberSystemUiController()
                val darkMode = isSystemInDarkTheme()
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colorScheme.background,
                    darkIcons = !darkMode
                )

                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost()
                }
            }
        }
    }
}
