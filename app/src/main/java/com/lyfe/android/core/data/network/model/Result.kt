package com.lyfe.android.core.data.network.model

sealed class Result<out T> {
	data class Success<T>(val body: T) : Result<T>()
	data class Failure<T>(val code: Int, val error: String?) : Result<T>()
	data class NetworkError<T>(val exception: Exception) : Result<T>()
	data class Unexpected<T>(val t: Throwable) : Result<T>()
}

fun <T, R> Result<T>.transform(onChange: (T) -> R): Result<R> {
	return when (this) {
		is Result.Unexpected -> Result.Unexpected(this.t)
		is Result.NetworkError -> Result.NetworkError(this.exception)
		is Result.Failure -> Result.Failure(code = this.code, error = this.error)
		is Result.Success -> Result.Success(onChange(this.body))
	}
}

fun <T1 : Any, T2 : Any, R : Any> Result<T1>.zip(
	other: Result<T2>,
	transform: (T1, T2) -> R
): Result<R> {
	return when {
		this is Result.Success && other is Result.Success -> Result.Success<R>(body = transform(this.body, other.body))
		this is Result.Unexpected -> Result.Unexpected(this.t)
		this is Result.NetworkError -> Result.NetworkError(this.exception)
		this is Result.Failure -> Result.Failure(code = this.code, error = this.error)
		other is Result.Unexpected -> Result.Unexpected(other.t)
		other is Result.NetworkError -> Result.NetworkError(other.exception)
		other is Result.Failure -> Result.Failure(code = other.code, error = other.error)
		else -> Result.Unexpected(Throwable("No Result"))
	}
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