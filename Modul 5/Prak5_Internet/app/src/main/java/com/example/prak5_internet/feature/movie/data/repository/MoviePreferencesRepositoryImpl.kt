package com.example.prak5_internet.feature.movie.data.repository

import com.example.prak5_internet.core.preferences.AppPreferences
import com.example.prak5_internet.feature.movie.domain.repository.MoviePreferencesRepository

class MoviePreferencesRepositoryImpl(
    private val prefs: AppPreferences
) : MoviePreferencesRepository {

    override fun saveLastOpenedMovieTitle(title: String) {
        prefs.saveLastOpenedMovieTitle(title)
    }

    override fun getLastOpenedMovieTitle(): String? {
        return prefs.getLastOpenedMovieTitle()
    }
}