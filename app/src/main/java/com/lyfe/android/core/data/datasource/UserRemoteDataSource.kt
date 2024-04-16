package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResult
import com.lyfe.android.core.data.model.GetUserBoardResult
import com.lyfe.android.core.data.model.UserInfo
import com.lyfe.android.core.data.network.model.Result

interface UserRemoteDataSource {
	suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResult>

	suspend fun getUserInfo(): Result<UserInfo>

	suspend fun putUserInfo(
		nickname: String,
		profileUrl: String
	): Result<UserInfo>

	suspend fun getUserBoard(lastId: Int?): Result<GetUserBoardResult>
}