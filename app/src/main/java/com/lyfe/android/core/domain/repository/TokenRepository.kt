package com.lyfe.android.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {

	suspend fun updateSignUpToken(
		signUpToken: String
	)

	suspend fun updateAccessToken(
		accessToken: String
	)

	suspend fun updateRefreshToken(
		refreshToken: String
	)

	fun getSignUpToken(): Flow<String>

	fun getAccessToken(): Flow<String>

	fun getRefreshToken(): Flow<String>

	suspend fun deleteSignUpToken()

	suspend fun deleteAllToken()
}