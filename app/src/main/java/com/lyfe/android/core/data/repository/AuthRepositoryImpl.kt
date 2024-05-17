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

	override suspend fun postUser(
		userToken: String,
		nickname: String
	): Result<PostUserResult> {
		val requestBody = PostUserRequest(
			userToken = userToken,
			nickname = nickname
		)
		return authDataSource.postUser(requestBody = requestBody)
	}

	override suspend fun authUser(
		socialType: String,
		authorizationCode: String,
		idToken: String,
		fcmToken: String
	): Result<AuthUserResult> {
		val requestBody = AuthUserRequest(
			socialType = socialType,
			authorizationCode = authorizationCode,
			idToken = idToken,
			fcmToken = fcmToken
		)
		return authDataSource.authUser(requestBody = requestBody)
	}

	override suspend fun reissueToken(token: String): Result<ReissueTokenResult> {
		val requestBody = ReissueTokenRequest(token = token)
		return authDataSource.reissueToken(requestBody = requestBody)
	}

	override suspend fun revoke(): Result<RevokeResult> {
		return authDataSource.revoke()
	}
}