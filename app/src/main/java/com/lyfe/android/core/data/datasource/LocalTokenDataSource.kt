package com.lyfe.android.core.data.datasource

import kotlinx.coroutines.flow.Flow

interface LocalTokenDataSource {

	suspend fun updateSignUpToken(signUpToken: String)

	suspend fun updateAccessToken(accessToken: String)

	suspend fun updateRefreshToken(refreshToken: String)

	fun getSignUpToken(): Flow<String>

	fun getAccessToken(): Flow<String>

	fun getRefreshToken(): Flow<String>

	suspend fun deleteSignUpToken()

	suspend fun deleteAllToken()

	suspend fun isAccessTokenExpired(): Boolean
}