package com.example.prak5_internet.core.network

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val code: Int? = null,
        val message: String
    ) : ApiResult<Nothing>()
    data object NetworkError : ApiResult<Nothing>()
}