package com.example.prak5_internet.feature.movie.domain.repository

import com.example.prak5_internet.core.network.ApiResult
import com.example.prak5_internet.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<ApiResult<List<Movie>>>
    suspend fun getMovieById(movieId: Int): ApiResult<Movie>
}