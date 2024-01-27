package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
	private val authService: AuthService
) : AuthDataSource {

	override suspend fun postUser(
		userToken: String,
		nickname: String
	): Result<PostUserResponse> {
		val body = mapOf("userToken" to userToken, "nickname" to nickname)
		return authService.postUser(body = body)
	}

	override suspend fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Result<AuthUserResponse> {
		val body = mapOf(
			"socialType" to socialType,
			"authorizationCode" to authorizationCode,
			"identityToken" to identityToken,
			"fcmToken" to fcmToken
		)
		return authService.authUser(body = body)
	}

	override suspend fun reissueToken(token: String): Result<ReissueTokenResponse> {
		val body = mapOf("token" to token)
		return authService.reissueToken(body = body)
	}
}