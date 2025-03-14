package dev.phyo.tm_events.util

sealed class DataResult<T>(val data:T? = null, val message: String? = null) {
    class Success<T>(data: T?): DataResult<T>(data, null)
    class Error<T>(message: String?): DataResult<T>(null, message)
    class Loading<T>(): DataResult<T>()
}