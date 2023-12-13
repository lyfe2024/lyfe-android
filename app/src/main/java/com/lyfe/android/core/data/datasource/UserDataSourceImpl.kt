package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
	private val userService: UserService
) : UserDataSource {

	override suspend fun fetchIsNicknameDuplicated(nickname: String): Result<CheckNicknameResponse> {
		return userService.fetchIsNicknameDuplicated(nickname = nickname)
	}
}