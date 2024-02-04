package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
	suspend fun fetchIsNicknameDuplicated(
		nickname: String
	): Result<CheckNicknameResponse>

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