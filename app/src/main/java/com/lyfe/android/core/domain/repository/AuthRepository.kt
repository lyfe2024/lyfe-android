package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.network.model.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

	fun postUser(
		userToken: String,
		nickname: String
	): Flow<Result<PostUserResponse>>

	fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	): Flow<Result<AuthUserResponse>>

	fun reissueToken(
		token: String
	): Flow<Result<ReissueTokenResponse>>
}