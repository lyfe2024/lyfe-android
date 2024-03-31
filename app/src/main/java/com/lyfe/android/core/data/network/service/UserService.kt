package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.CheckNicknameResponse
import com.lyfe.android.core.data.model.GetUserInfoResponse
import com.lyfe.android.core.data.model.PutUserInfoRequest
import com.lyfe.android.core.data.model.PutUserInfoResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
	// 유저 API 조회
	@GET("/v1/users/check-nickname/{nickname}")
	suspend fun checkNicknameDuplicated(
		@Path("nickname") nickname: String
	): Result<CheckNicknameResponse>

	@GET("/v1/users/me")
	suspend fun getUserInfo(): Result<GetUserInfoResponse>

	@PUT("/v1/users/me")
	suspend fun putUserInfo(
		@Body body: PutUserInfoRequest
	): Result<PutUserInfoResponse>
}