package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.AuthDataSource
import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authDataSource: AuthDataSource
) : AuthRepository {

	override suspend fun postUser(userToken: String, nickname: String): Result<PostUserResponse> {
		return authDataSource.postUser(userToken, nickname)
	}

	override suspend fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Result<AuthUserResponse> {
		return authDataSource.authUser(socialType, authorizationCode, identityToken, fcmToken)
	}

	override suspend fun reissueToken(token: String): Result<ReissueTokenResponse> {
		return authDataSource.reissueToken(token)
	}
}