package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import com.lyfe.android.core.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
	private val localTokenDataSource: LocalTokenDataSource
) : TokenRepository {

	override suspend fun updateSignUpToken(signUpToken: String) {
		localTokenDataSource.updateSignUpToken(signUpToken)
	}

	override suspend fun updateAccessToken(accessToken: String) {
		localTokenDataSource.updateAccessToken(accessToken)
	}

	override suspend fun updateRefreshToken(refreshToken: String) {
		localTokenDataSource.updateRefreshToken(refreshToken)
	}

	override fun getSignUpToken(): Flow<String> {
		return localTokenDataSource.getSignUpToken()
	}

	override fun getAccessToken(): Flow<String> {
		return localTokenDataSource.getAccessToken()
	}

	override fun getRefreshToken(): Flow<String> {
		return localTokenDataSource.getRefreshToken()
	}

	override suspend fun deleteSignUpToken() {
		localTokenDataSource.deleteSignUpToken()
	}

	override suspend fun deleteAllToken() {
		localTokenDataSource.deleteAllToken()
	}
}