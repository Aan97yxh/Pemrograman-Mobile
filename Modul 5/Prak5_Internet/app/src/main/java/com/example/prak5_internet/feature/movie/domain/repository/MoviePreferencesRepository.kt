package com.example.prak5_internet.feature.movie.domain.repository

interface MoviePreferencesRepository {
    fun saveLastOpenedMovieTitle(title: String)
    fun getLastOpenedMovieTitle(): String?
}