package com.example.appcolegio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appcolegio.navegacion.Navegar
import com.example.appcolegio.ui.theme.AppColegioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppColegioTheme {
                Navegar()
            }
        }
    }
}
