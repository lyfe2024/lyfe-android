package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResult
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.PostUserResult
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResult
import com.lyfe.android.core.data.model.RevokeResult
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AuthService
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
	private val authService: AuthService
) : AuthDataSource {

	override suspend fun postUser(requestBody: PostUserRequest): Result<PostUserResult> {
		return authService.postUser(body = requestBody)
	}

	override suspend fun authUser(requestBody: AuthUserRequest): Result<AuthUserResult> {
		return authService.authUser(body = requestBody)
	}

	override suspend fun reissueToken(requestBody: ReissueTokenRequest): Result<ReissueTokenResult> {
		return authService.reissueToken(body = requestBody)
	}

	override suspend fun revoke(): Result<RevokeResult> {
		return authService.revoke()
	}
}