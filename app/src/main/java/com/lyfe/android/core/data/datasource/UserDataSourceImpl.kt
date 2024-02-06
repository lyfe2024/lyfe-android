package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.model.GetUserBoardResponse
import com.lyfe.android.core.data.model.GetUserInfoResponse
import com.lyfe.android.core.data.model.PutUserInfoResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
	private val userService: UserService
) : UserDataSource {

	override suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResponse> {
		return userService.checkNicknameDuplicated(nickname = nickname)
	}

	override suspend fun getUserInfo(): Result<GetUserInfoResponse> {
		return userService.getUserInfo()
	}

	override suspend fun putUserInfo(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	): Result<PutUserInfoResponse> {
		val body = mapOf(
			"nickname" to nickname,
			"profileUrl" to profileUrl,
			"width" to width,
			"height" to height
		)
		return userService.putUserInfo(body)
	}

	override suspend fun getUserBoard(lastId: Int?): Result<GetUserBoardResponse> {
		return userService.getUserBoard(lastId = lastId)
	}
}