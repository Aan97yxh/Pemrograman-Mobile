package com.example.prak5_internet.feature.movie.data.local.mapper

import com.example.prak5_internet.feature.movie.data.local.MovieEntity
import com.example.prak5_internet.feature.movie.data.remote.dto.MovieDto
import com.example.prak5_internet.feature.movie.domain.model.Movie

fun MovieDto.toEntity(): MovieEntity {
    val queryTitle = title.replace(" ", "+")
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        genreIds = genreIds.joinToString(","),
        popularity = popularity,
        youtubeTrailerUrl = "https://www.youtube.com/results?search_query=$queryTitle+official+trailer"
    )
}

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
    releaseDate = releaseDate ?: "-",
    genreIds = if (genreIds.isBlank()) emptyList()
    else genreIds.split(",").mapNotNull { it.trim().toIntOrNull() },
    popularity = popularity,
    youtubeTrailerUrl = youtubeTrailerUrl
)

fun MovieDto.toDomain(): Movie {
    val queryTitle = title.replace(" ", "+")
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate ?: "-",
        genreIds = genreIds,
        popularity = popularity,
        youtubeTrailerUrl = "https://www.youtube.com/results?search_query=$queryTitle+official+trailer"
    )
}