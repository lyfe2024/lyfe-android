package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResponse
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.PostUserResponse
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResponse
import com.lyfe.android.core.data.model.DeleteAccountResponse
import com.lyfe.android.core.data.network.model.Result

interface AuthDataSource {

	suspend fun postUser(
		requestBody: PostUserRequest
	): Result<PostUserResponse>

	suspend fun authUser(
		requestBody: AuthUserRequest
	): Result<AuthUserResponse>

	suspend fun reissueToken(
		requestBody: ReissueTokenRequest
	): Result<ReissueTokenResponse>

	suspend fun revoke(): Result<DeleteAccountResponse>
}