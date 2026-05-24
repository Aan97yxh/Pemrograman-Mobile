package com.example.prak5_internet.core.preferences

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // ── Dark Mode ──
    private val _isDarkMode = MutableStateFlow(prefs.getBoolean(KEY_DARK_MODE, false))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun setDarkMode(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_DARK_MODE, enabled) }
        _isDarkMode.value = enabled
    }

    // ── Last Opened Movie ──
    fun saveLastOpenedMovieTitle(title: String) {
        prefs.edit { putString(KEY_LAST_MOVIE, title) }
    }

    fun getLastOpenedMovieTitle(): String? {
        return prefs.getString(KEY_LAST_MOVIE, null)
    }

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_LAST_MOVIE = "last_opened_movie"
    }
}