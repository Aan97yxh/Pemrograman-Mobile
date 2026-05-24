package com.example.prak5_internet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prak5_internet.app.navigation.AppNavigation
import com.example.prak5_internet.core.preferences.AppPreferences
import com.example.prak5_internet.ui.theme.MovieTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appPreferences = AppPreferences(this)

        setContent {
            val isDarkMode by appPreferences.isDarkMode.collectAsStateWithLifecycle()

            MovieTheme(darkTheme = isDarkMode) {
                AppNavigation(appPreferences = appPreferences)
            }
        }
    }
}