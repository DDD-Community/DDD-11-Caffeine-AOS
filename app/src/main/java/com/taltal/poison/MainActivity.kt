package com.taltal.poison

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.taltal.poison.ui.navigation.PoisonRootRoute
import com.taltal.poison.ui.theme.PoisonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PoisonTheme {
                PoisonRootRoute()
            }
        }
    }
}

