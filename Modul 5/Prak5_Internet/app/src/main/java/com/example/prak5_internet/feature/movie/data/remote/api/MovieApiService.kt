package com.example.prak5_internet.feature.movie.data.remote.api

import com.example.prak5_internet.feature.movie.data.remote.dto.MovieDto
import com.example.prak5_internet.feature.movie.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDto
}