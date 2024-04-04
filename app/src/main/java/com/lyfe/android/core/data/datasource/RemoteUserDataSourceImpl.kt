package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResult
import com.lyfe.android.core.data.model.GetUserBoardResult
import com.lyfe.android.core.data.model.PutUserInfoRequest
import com.lyfe.android.core.data.model.UserInfo
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.UserService
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
	private val userService: UserService
) : RemoteUserDataSource {

	override suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResult> {
		return userService.checkNicknameDuplicated(nickname = nickname)
	}

	override suspend fun getUserInfo(): Result<UserInfo> {
		return userService.getUserInfo()
	}

	override suspend fun putUserInfo(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	): Result<UserInfo> {
		val body = PutUserInfoRequest(nickname, profileUrl, width, height)
		return userService.putUserInfo(body)
	}

	override suspend fun getUserBoard(lastId: Int?): Result<GetUserBoardResult> {
		return userService.getUserBoard(lastId = lastId)
	}
}