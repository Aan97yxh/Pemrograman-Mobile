package com.example.prak5_internet.core.network

import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(call())
    } catch (e: HttpException) {
        val code = e.code()
        val message = when (code) {
            401 -> "Invalid API key"
            404 -> "Not found"
            429 -> "Rate limit exceeded"
            500 -> "Server error"
            else -> e.message ?: "HTTP error $code"
        }
        Timber.e(e, "HTTP $code: $message")
        ApiResult.Error(code = code, message = message)
    } catch (e: IOException) {
        Timber.e(e, "Network error")
        ApiResult.NetworkError
    } catch (e: Exception) {
        Timber.e(e, "Unexpected error")
        ApiResult.Error(message = e.message ?: "Unexpected error")
    }
}