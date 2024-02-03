package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.LocalUserDataSource
import com.lyfe.android.core.data.datasource.RemoteUserDataSource
import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val remoteUserDataSource: RemoteUserDataSource,
	private val localUserDataSource: LocalUserDataSource
) : UserRepository {

	override suspend fun fetchIsNicknameDuplicated(nickname: String): Result<CheckNicknameResponse> {
		return remoteUserDataSource.checkNicknameDuplicated(nickname)
	}

	override suspend fun saveUserAccessToken(accessToken: String) {
		localUserDataSource.saveUserAccessToken(accessToken)
	}

	override suspend fun saveUserRefreshToken(refreshToken: String) {
		localUserDataSource.saveUserRefreshToken(refreshToken)
	}

	override fun getUserAccessToken(): Flow<String> {
		return localUserDataSource.getUserAccessToken()
	}

	override fun getUserRefreshToken(): Flow<String> {
		return localUserDataSource.getUserRefreshToken()
	}
}