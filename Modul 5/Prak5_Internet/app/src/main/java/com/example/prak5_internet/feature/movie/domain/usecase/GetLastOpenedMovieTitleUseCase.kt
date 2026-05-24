package com.example.prak5_internet.feature.movie.domain.usecase

import com.example.prak5_internet.feature.movie.domain.repository.MoviePreferencesRepository

class GetLastOpenedMovieTitleUseCase(
    private val repository: MoviePreferencesRepository
) {
    operator fun invoke(): String? =
        repository.getLastOpenedMovieTitle()
}