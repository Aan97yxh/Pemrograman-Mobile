package com.example.prak5_internet.feature.movie.domain.usecase

import com.example.prak5_internet.core.network.ApiResult
import com.example.prak5_internet.feature.movie.domain.model.Movie
import com.example.prak5_internet.feature.movie.domain.repository.MovieRepository

class GetMovieByIdUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): ApiResult<Movie> =
        repository.getMovieById(movieId)
}