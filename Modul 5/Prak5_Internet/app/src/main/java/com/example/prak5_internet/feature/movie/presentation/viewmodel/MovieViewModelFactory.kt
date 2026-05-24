package com.example.prak5_internet.feature.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prak5_internet.feature.movie.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.GetMovieByIdUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.GetPopularMoviesUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.SaveLastOpenedMovieUseCase

class MovieViewModelFactory(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val saveLastOpenedMovieUseCase: SaveLastOpenedMovieUseCase,
    private val getLastOpenedMovieTitleUseCase: GetLastOpenedMovieTitleUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getMovieByIdUseCase = getMovieByIdUseCase,
            saveLastOpenedMovieUseCase = saveLastOpenedMovieUseCase,
            getLastOpenedMovieTitleUseCase = getLastOpenedMovieTitleUseCase
        ) as T
    }
}