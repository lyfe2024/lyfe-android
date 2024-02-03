package com.lyfe.android.core.data.datasource

import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {

	suspend fun saveUserAccessToken(accessToken: String)

	suspend fun saveUserRefreshToken(refreshToken: String)

	fun getUserAccessToken(): Flow<String>

	fun getUserRefreshToken(): Flow<String>
}