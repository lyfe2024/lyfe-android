package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.model.GetUserInfoResponse
import com.lyfe.android.core.data.model.PutUserInfoRequest
import com.lyfe.android.core.data.model.PutUserInfoResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.UserService
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
	private val userService: UserService
) : RemoteUserDataSource {

	override suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResponse> {
		return userService.checkNicknameDuplicated(nickname = nickname)
	}

	override suspend fun getUserInfo(): Result<GetUserInfoResponse> {
		return userService.getUserInfo()
	}

	override suspend fun putUserInfo(
		nickname: String,
		profileUrl: String
	): Result<PutUserInfoResponse> {
		val body = PutUserInfoRequest(nickname, profileUrl)
		return userService.putUserInfo(body)
	}
}