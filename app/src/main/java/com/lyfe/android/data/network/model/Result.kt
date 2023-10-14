package com.lyfe.android.data.network.model

sealed class Result<out T : Any> {
	data class Success<T : Any>(val body: T?) : Result<T>()
	data class Failure(val code: Int, val error: String?) : Result<Nothing>()
	data class NetworkError(val exception: Exception) : Result<Nothing>()
	data class Unexpected(val t: Throwable?) : Result<Nothing>()
}