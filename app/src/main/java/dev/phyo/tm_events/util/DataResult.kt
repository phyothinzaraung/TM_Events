package dev.phyo.tm_events.util

sealed class DataResult<out T> {
    class Success<out T>(val data: T): DataResult<T>()
    class Error<T>(val message: String?): DataResult<T>()
    class Loading<T>(): DataResult<T>()
}