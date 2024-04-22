package com.lyfe.android.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {

	/**
	 * SignUpToken Function
	 */
	fun getSignUpToken(): Flow<String>

	suspend fun updateSignUpToken(
		signUpToken: String
	)

	suspend fun deleteSignUpToken()

	/**
	 * AccessToken Function
	 */
	fun getAccessToken(): Flow<String?>

	suspend fun updateAccessToken(
		accessToken: String
	)

	/**
	 * RefreshToken Function
	 */
	fun getRefreshToken(): Flow<String?>

	suspend fun updateRefreshToken(
		refreshToken: String
	)

	/**
	 * Util Function
	 */
	suspend fun deleteAllToken()
}