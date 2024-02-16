package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.Page
import com.lyfe.android.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
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

	fun getUserInfo(): Flow<User>

	suspend fun fetchIsNicknameDuplicated(
		nickname: String
	): Flow<Boolean>

	suspend fun putUserInfo(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	): Flow<User>

	fun getUserBoard(lastId: Int?): Flow<Pair<List<Feed>, Page>>
}