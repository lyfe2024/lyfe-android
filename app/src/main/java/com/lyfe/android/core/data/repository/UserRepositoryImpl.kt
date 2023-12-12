package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.UserDataSource
import com.lyfe.android.core.data.model.CheckNicknameResult
import com.lyfe.android.core.data.model.Response
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val userDataSource: UserDataSource
) : UserRepository {

	override suspend fun fetchIsNicknameDuplicated(nickname: String): Result<Response<CheckNicknameResult>> {
		return userDataSource.fetchIsNicknameDuplicated(nickname)
	}
}