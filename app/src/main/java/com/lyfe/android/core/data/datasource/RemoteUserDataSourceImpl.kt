package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.UserService
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
	private val userService: UserService
) : RemoteUserDataSource {

	override suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResponse> {
		return userService.checkNicknameDuplicated(nickname = nickname)
	}
}