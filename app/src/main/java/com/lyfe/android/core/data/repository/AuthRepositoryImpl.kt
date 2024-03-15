package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.AuthDataSource
import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.DeleteAccountResponse
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authDataSource: AuthDataSource
) : AuthRepository {

	override suspend fun postUser(userToken: String, nickname: String): Result<PostUserResponse> {
		val requestBody = PostUserRequest(userToken, nickname)
		return authDataSource.postUser(requestBody)
	}

	override suspend fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Result<AuthUserResponse> {
		val requestBody = AuthUserRequest(socialType, authorizationCode, identityToken, fcmToken)
		return authDataSource.authUser(requestBody)
	}

	override suspend fun reissueToken(token: String): Result<ReissueTokenResponse> {
		val requestBody = ReissueTokenRequest(token)
		return authDataSource.reissueToken(requestBody)
	}

	override suspend fun revoke(): Result<DeleteAccountResponse> {
		return authDataSource.revoke()
	}
}