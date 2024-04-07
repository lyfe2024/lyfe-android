package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.AuthUserRequest
import com.lyfe.android.core.data.model.AuthUserResult
import com.lyfe.android.core.data.model.PostUserRequest
import com.lyfe.android.core.data.model.PostUserResult
import com.lyfe.android.core.data.model.ReissueTokenRequest
import com.lyfe.android.core.data.model.ReissueTokenResult
import com.lyfe.android.core.data.model.RevokeResult
import com.lyfe.android.core.data.network.model.Result

interface AuthDataSource {

	suspend fun postUser(
		requestBody: PostUserRequest
	): Result<PostUserResult>

	suspend fun authUser(
		requestBody: AuthUserRequest
	): Result<AuthUserResult>

	suspend fun reissueToken(
		requestBody: ReissueTokenRequest
	): Result<ReissueTokenResult>

	suspend fun revoke(): Result<RevokeResult>
}