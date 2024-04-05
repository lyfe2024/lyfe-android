package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.TokenLocalDataSource
import com.lyfe.android.core.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
	private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {

	/**
	 * SignUpToken Function
	 */
	override fun getSignUpToken(): Flow<String> {
		return tokenLocalDataSource.getSignUpToken()
	}

	override suspend fun updateSignUpToken(signUpToken: String) {
		tokenLocalDataSource.updateSignUpToken(signUpToken)
	}

	override suspend fun deleteSignUpToken() {
		tokenLocalDataSource.deleteSignUpToken()
	}

	/**
	 * AccessToken Function
	 */
	override fun getAccessToken(): Flow<String> {
		return tokenLocalDataSource.getAccessToken()
	}

	override suspend fun updateAccessToken(accessToken: String) {
		tokenLocalDataSource.updateAccessToken(accessToken)
	}

	/**
	 * RefreshToken Function
	 */
	override fun getRefreshToken(): Flow<String> {
		return tokenLocalDataSource.getRefreshToken()
	}

	override suspend fun updateRefreshToken(refreshToken: String) {
		tokenLocalDataSource.updateRefreshToken(refreshToken)
	}

	/**
	 * Util Function
	 */
	override suspend fun deleteAllToken() {
		tokenLocalDataSource.deleteAllToken()
	}
}