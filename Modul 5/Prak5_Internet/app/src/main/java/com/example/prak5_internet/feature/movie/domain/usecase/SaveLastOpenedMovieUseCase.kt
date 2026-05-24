package com.example.prak5_internet.feature.movie.domain.usecase

import com.example.prak5_internet.feature.movie.domain.repository.MoviePreferencesRepository

class SaveLastOpenedMovieUseCase(
    private val repository: MoviePreferencesRepository
) {
    operator fun invoke(title: String) =
        repository.saveLastOpenedMovieTitle(title)
}