package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResult
import com.lyfe.android.core.data.model.Response
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
	private val userService: UserService
) : UserDataSource {

	override suspend fun fetchIsNicknameDuplicated(nickname: String): Result<Response<CheckNicknameResult>> {
		return userService.fetchIsNicknameDuplicated(nickname = nickname)
	}
}