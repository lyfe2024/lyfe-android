package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.AuthDataSource
import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResult
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.PostUserResult
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResult
import com.lyfe.android.core.data.model.RevokeResult
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authDataSource: AuthDataSource
) : AuthRepository {

	override suspend fun postUser(userToken: String, nickname: String): Result<PostUserResult> {
		val requestBody = PostUserRequest(userToken, nickname)
		return authDataSource.postUser(requestBody)
	}

	override suspend fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Result<AuthUserResult> {
		val requestBody = AuthUserRequest(socialType, authorizationCode, identityToken, fcmToken)
		return authDataSource.authUser(requestBody)
	}

	override suspend fun reissueToken(token: String): Result<ReissueTokenResult> {
		val requestBody = ReissueTokenRequest(token)
		return authDataSource.reissueToken(requestBody)
	}

	override suspend fun revoke(): Result<RevokeResult> {
		return authDataSource.revoke()
	}
}