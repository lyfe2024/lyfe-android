package com.lyfe.android.core.data.datasource

import kotlinx.coroutines.flow.Flow

interface TokenDataSource {
	/**
	 * SignUpToken Function
	 */
	fun getSignUpToken(): Flow<String>
	suspend fun updateSignUpToken(signUpToken: String)
	suspend fun deleteSignUpToken()

	/**
	 * AccessToken Function
	 */
	fun getAccessToken(): Flow<String>
	suspend fun updateAccessToken(accessToken: String)
	suspend fun isAccessTokenExpired(): Boolean

	/**
	 * RefreshToken Function
	 */
	fun getRefreshToken(): Flow<String>
	suspend fun updateRefreshToken(refreshToken: String)

	/**
	 * Util Function
	 */
	suspend fun deleteAllToken()
}