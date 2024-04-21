package com.lyfe.android.core.data.network.model

sealed class Result<out T : Any> {
	data class Success<T : Any>(val body: T) : Result<T>()
	data class Failure<T : Any>(val code: Int, val error: String?) : Result<T>()
	data class NetworkError<T : Any>(val exception: Exception) : Result<T>()
	data class Unexpected<T : Any>(val t: Throwable) : Result<T>()
}

suspend fun <T : Any> Result<T>.onSuccess(
	executable: suspend (T) -> Unit
): Result<T> = apply {
	if (this is Result.Success<T>) {
		executable(body)
	}
}

suspend fun <T : Any> Result<T>.onFailure(
	executable: suspend (code: Int, error: String?) -> Unit
): Result<T> = apply {
	if (this is Result.Failure<T>) {
		executable(code, error)
	}
}

suspend fun <T : Any> Result<T>.onException(
	executable: suspend (e: Exception) -> Unit
): Result<T> = apply {
	if (this is Result.NetworkError<T>) {
		executable(exception)
	}
}

suspend fun <T : Any> Result<T>.onUnexpected(
	executable: suspend (e: Throwable) -> Unit
): Result<T> = apply {
	if (this is Result.Unexpected<T>) {
		executable(t)
	}
}