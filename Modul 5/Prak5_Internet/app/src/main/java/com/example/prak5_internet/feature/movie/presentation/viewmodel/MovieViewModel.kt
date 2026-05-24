package com.example.prak5_internet.feature.movie.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prak5_internet.core.common.UiState
import com.example.prak5_internet.core.network.ApiResult
import com.example.prak5_internet.feature.movie.domain.model.Movie
import com.example.prak5_internet.feature.movie.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.GetMovieByIdUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.GetPopularMoviesUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.SaveLastOpenedMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val saveLastOpenedMovieUseCase: SaveLastOpenedMovieUseCase,
    private val getLastOpenedMovieTitleUseCase: GetLastOpenedMovieTitleUseCase
) : ViewModel() {

    private val _moviesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val moviesState: StateFlow<UiState<List<Movie>>> = _moviesState.asStateFlow()

    private val _detailState = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val detailState: StateFlow<UiState<Movie>> = _detailState.asStateFlow()

    private val _lastOpenedTitle = MutableStateFlow<String?>(null)
    val lastOpenedTitle: StateFlow<String?> = _lastOpenedTitle.asStateFlow()

    init {
        loadPopularMovies()
        _lastOpenedTitle.value = getLastOpenedMovieTitleUseCase()
        Timber.d("Last opened movie: ${_lastOpenedTitle.value}")
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            _moviesState.value = UiState.Loading
            getPopularMoviesUseCase().collect { result ->
                _moviesState.value = when (result) {
                    is ApiResult.Success -> {
                        Timber.d("Loaded ${result.data.size} movies")
                        UiState.Success(result.data)
                    }
                    is ApiResult.Error -> {
                        Timber.e("Error: ${result.message}")
                        UiState.Error(result.message)
                    }
                    is ApiResult.NetworkError -> {
                        Timber.e("Network error")
                        UiState.Error("No internet connection")
                    }
                }
            }
        }
    }

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            when (val result = getMovieByIdUseCase(movieId)) {
                is ApiResult.Success -> {
                    Timber.d("Detail loaded: ${result.data.title}")
                    _detailState.value = UiState.Success(result.data)
                }
                is ApiResult.Error -> {
                    Timber.e("Detail error: ${result.message}")
                    _detailState.value = UiState.Error(result.message)
                }
                is ApiResult.NetworkError -> {
                    _detailState.value = UiState.Error("No internet connection")
                }
            }
        }
    }

    fun onMovieOpened(movie: Movie) {
        saveLastOpenedMovieUseCase(movie.title)
        _lastOpenedTitle.value = movie.title
        Timber.d("Movie opened: ${movie.title}")
    }
}