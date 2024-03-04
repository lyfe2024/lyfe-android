package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AuthService
import javax.inject.Inject
import javax.inject.Named

class AuthDataSourceImpl @Inject constructor(
	@Named("lyfe") private val authService: AuthService
) : AuthDataSource {

	override suspend fun postUser(requestBody: PostUserRequest): Result<PostUserResponse> {
		return authService.postUser(body = requestBody)
	}

	override suspend fun authUser(requestBody: AuthUserRequest): Result<AuthUserResponse> {
		return authService.authUser(body = requestBody)
	}

	override suspend fun reissueToken(requestBody: ReissueTokenRequest): Result<ReissueTokenResponse> {
		return authService.reissueToken(body = requestBody)
	}
}