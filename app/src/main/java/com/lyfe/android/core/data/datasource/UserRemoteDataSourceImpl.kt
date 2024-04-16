package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResult
import com.lyfe.android.core.data.model.GetUserBoardResult
import com.lyfe.android.core.data.model.PutUserInfoRequest
import com.lyfe.android.core.data.model.UserInfo
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
	private val userService: UserService
) : UserRemoteDataSource {

	override suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResult> {
		return userService.checkNicknameDuplicated(nickname = nickname)
	}

	override suspend fun getUserInfo(): Result<UserInfo> {
		return userService.getUserInfo()
	}

	override suspend fun putUserInfo(
		nickname: String,
		profileUrl: String
	): Result<UserInfo> {
		val body = PutUserInfoRequest(nickname, profileUrl)
		return userService.putUserInfo(body)
	}

	override suspend fun getUserBoard(lastId: Int?): Result<GetUserBoardResult> {
		return userService.getUserBoard(lastId = lastId)
	}
}