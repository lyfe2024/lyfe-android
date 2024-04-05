package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResult
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.PostUserResult
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResult
import com.lyfe.android.core.data.model.RevokeResult
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

	// 회원가입 API
	@POST("/v1/auth/join")
	suspend fun postUser(@Body body: PostUserRequest): Result<PostUserResult>

	// 소셜 로그인 API
	@POST("/v1/auth/login")
	suspend fun authUser(@Body body: AuthUserRequest): Result<AuthUserResult>

	// 토큰 재발행 API
	@POST("/v1/auth/reissue")
	suspend fun reissueToken(@Body body: ReissueTokenRequest): Result<ReissueTokenResult>

	// 회원탈퇴 API
	@POST("/v1/auth/revoke")
	suspend fun revoke(): Result<RevokeResult>
}