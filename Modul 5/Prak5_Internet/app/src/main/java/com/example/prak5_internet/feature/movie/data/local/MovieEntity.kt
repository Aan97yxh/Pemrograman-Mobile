package com.example.prak5_internet.feature.movie.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String?,
    val genreIds: String,
    val popularity: Double,
    val youtubeTrailerUrl: String,
    val cachedAt: Long = System.currentTimeMillis()
)