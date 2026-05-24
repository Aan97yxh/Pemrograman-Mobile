package com.example.prak5_internet.feature.movie.domain.usecase

import com.example.prak5_internet.core.network.ApiResult
import com.example.prak5_internet.feature.movie.domain.model.Movie
import com.example.prak5_internet.feature.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<ApiResult<List<Movie>>> =
        repository.getPopularMovies()
}