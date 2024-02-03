package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
	suspend fun fetchIsNicknameDuplicated(
		nickname: String
	): Result<CheckNicknameResponse>

	suspend fun saveUserAccessToken(
		accessToken: String
	)

	suspend fun saveUserRefreshToken(
		refreshToken: String
	)

	fun getUserAccessToken(): Flow<String>

	fun getUserRefreshToken(): Flow<String>
}