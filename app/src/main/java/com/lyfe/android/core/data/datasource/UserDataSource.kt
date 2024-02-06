package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.model.GetUserBoardResponse
import com.lyfe.android.core.data.model.GetUserInfoResponse
import com.lyfe.android.core.data.model.PutUserInfoResponse
import com.lyfe.android.core.data.network.model.Result

interface UserDataSource {
	suspend fun checkNicknameDuplicated(nickname: String): Result<CheckNicknameResponse>

	suspend fun getUserInfo(): Result<GetUserInfoResponse>

	suspend fun putUserInfo(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	): Result<PutUserInfoResponse>

	suspend fun getUserBoard(lastId: Int?): Result<GetUserBoardResponse>
}