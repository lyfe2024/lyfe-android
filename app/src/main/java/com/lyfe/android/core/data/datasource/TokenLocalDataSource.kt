package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.network.token.TokenManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenLocalDataSource @Inject constructor(
	private val tokenManager: TokenManager
) : TokenDataSource {

	/**
	 * SignUpToken Function
	 */
	override fun getSignUpToken() = tokenManager.getSignUpToken()

	override suspend fun updateSignUpToken(signUpToken: String) = tokenManager.updateSignUpToken(signUpToken)

	override suspend fun deleteSignUpToken() = tokenManager.deleteAllToken()

	/**
	 * AccessToken Function
	 */
	override fun getAccessToken(): Flow<String> = tokenManager.getAccessToken()

	override suspend fun updateAccessToken(accessToken: String) = tokenManager.updateAccessToken(accessToken)

	override suspend fun isAccessTokenExpired(): Boolean = tokenManager.isAccessTokenExpired()

	/**
	 * RefreshToken Function
	 */
	override fun getRefreshToken(): Flow<String> = tokenManager.getRefreshToken()

	override suspend fun updateRefreshToken(refreshToken: String) = tokenManager.updateRefreshToken(refreshToken)

	/**
	 * Util Function
	 */
	override suspend fun deleteAllToken() = tokenManager.deleteAllToken()
}