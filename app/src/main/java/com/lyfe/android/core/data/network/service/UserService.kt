package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
	// 유저 API 조회
	@GET("/v1/users/check-nickname/{nickname}")
	suspend fun fetchIsNicknameDuplicated(
		@Path("nickname") nickname: String
	): Result<CheckNicknameResponse>
}