package com.walmart.venkata.data.common

/**
 * Represents UI state for API responses: Loading, Success or Error
 */
sealed class ApiResponseState<out T> {
    data object Loading: ApiResponseState<Nothing>()
    data class Success<T>(val data: T): ApiResponseState<T>()
    data class Error(val message: String): ApiResponseState<Nothing>()
}