package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.model.DeleteAccountResponse
import com.lyfe.android.core.data.network.model.Result

interface AuthDataSource {

	suspend fun postUser(
		userToken: String,
		nickname: String
	): Result<PostUserResponse>

	suspend fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Result<AuthUserResponse>

	suspend fun reissueToken(
		token: String
	): Result<ReissueTokenResponse>

	suspend fun revoke(): Result<DeleteAccountResponse>
}