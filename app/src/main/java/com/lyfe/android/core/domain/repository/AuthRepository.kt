package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.AuthUserResult
import com.lyfe.android.core.data.model.PostUserResult
import com.lyfe.android.core.data.model.ReissueTokenResult
import com.lyfe.android.core.data.model.RevokeResult
import com.lyfe.android.core.data.network.model.Result

interface AuthRepository {

	suspend fun postUser(
		userToken: String,
		nickname: String
	): Result<PostUserResult>

	suspend fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Result<AuthUserResult>

	suspend fun reissueToken(
		token: String
	): Result<ReissueTokenResult>

	suspend fun revoke(): Result<RevokeResult>
}