package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.model.DeleteAccountResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

	// 회원가입 API
	@POST("/v1/auth/join")
	suspend fun postUser(@Body body: PostUserRequest): Result<PostUserResponse>

	// 소셜 로그인 API
	@POST("/v1/auth/login")
	suspend fun authUser(@Body body: AuthUserRequest): Result<AuthUserResponse>

	// 토큰 재발행 API
	@POST("/v1/auth/reissue")
	suspend fun reissueToken(@Body body: ReissueTokenRequest): Result<ReissueTokenResponse>

	// 회원탈퇴 API
	@POST("/v1/auth/revoke")
	suspend fun revoke(): Result<DeleteAccountResponse>
}