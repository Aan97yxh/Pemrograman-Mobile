package com.example.prak5_internet.feature.movie.data.repository

import com.example.prak5_internet.core.network.ApiResult
import com.example.prak5_internet.core.network.safeApiCall
import com.example.prak5_internet.feature.movie.data.local.MovieDao
import com.example.prak5_internet.feature.movie.data.local.mapper.toDomain
import com.example.prak5_internet.feature.movie.data.local.mapper.toEntity
import com.example.prak5_internet.feature.movie.data.remote.api.MovieApiService
import com.example.prak5_internet.feature.movie.domain.model.Movie
import com.example.prak5_internet.feature.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import timber.log.Timber

class MovieRepositoryImpl(
    private val api: MovieApiService,
    private val dao: MovieDao
) : MovieRepository {

    companion object {
        private const val CACHE_TTL_MS = 60 * 60 * 1000L
    }

    override fun getPopularMovies(): Flow<ApiResult<List<Movie>>> = flow {
        // 1. Emit cache dulu dari Room (cache-first)
        try {
            val currentCache = dao.getMovies().first()
            if (currentCache.isNotEmpty()) {
                Timber.d("Menampilkan cache awal ke UI (${currentCache.size} item)")
                emit(ApiResult.Success(currentCache.map { it.toDomain() }))
            }
        } catch (e: Exception) {
            Timber.e(e, "Gagal mengambil cache awal")
        }

        // 2. Cek apakah cache masih fresh
        val oldestCache = dao.getOldestCacheTime()
        val isCacheExpired = oldestCache == null ||
                (System.currentTimeMillis() - oldestCache) > CACHE_TTL_MS

        if (!isCacheExpired) {
            Timber.d("Cache masih fresh, skip network call")
            return@flow
        }

        // 3. Fetch dari network jika cache expired
        Timber.d("Cache expired atau kosong, fetching dari network...")
        when (val result = safeApiCall { api.getPopularMovies() }) {
            is ApiResult.Success -> {
                val entities = result.data.results.map { it.toEntity() }
                dao.clearMovies()
                dao.insertMovies(entities)

                // Ambil data segar yang sudah bersatu dengan kolom trailer dan timestamp lokal Room
                val updatedCache = dao.getMovies().first()
                emit(ApiResult.Success(updatedCache.map { it.toDomain() }))
                Timber.d("Sinkronisasi internet sukses, database diperbarui.")
            }
            is ApiResult.Error -> {
                Timber.e("API Error ${result.code}: ${result.message}")
                emit(result)
            }
            is ApiResult.NetworkError -> {
                Timber.e("Network error, serving stale cache")
                emit(ApiResult.NetworkError)
            }
        }
    }

    override suspend fun getMovieById(movieId: Int): ApiResult<Movie> {
        // Cek local dulu
        val local = dao.getMovieById(movieId)
        if (local != null) {
            Timber.d("Movie $movieId ditemukan di cache")
            return ApiResult.Success(local.toDomain())
        }
        // Fallback ke network (data DTO otomatis mendapat generate link youtube dari mapper baru)
        return when (val result = safeApiCall { api.getMovieById(movieId) }) {
            is ApiResult.Success -> ApiResult.Success(result.data.toDomain())
            is ApiResult.Error -> result
            is ApiResult.NetworkError -> ApiResult.NetworkError
        }
    }
}