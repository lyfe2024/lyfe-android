package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.RemoteUserDataSource
import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val remoteUserDataSource: RemoteUserDataSource
) : UserRepository {

	override suspend fun fetchIsNicknameDuplicated(nickname: String): Result<CheckNicknameResponse> {
		return remoteUserDataSource.checkNicknameDuplicated(nickname)
	}
}