package com.raithavarta.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.raithavarta.app.ui.RaithaTheme
import com.raithavarta.app.ui.RaithaVartaApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaithaTheme {
                RaithaVartaApp()
            }
        }
    }
}
