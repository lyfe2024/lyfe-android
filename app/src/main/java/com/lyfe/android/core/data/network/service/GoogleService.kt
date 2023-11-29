package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.GoogleTokenResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleService {

	// 구글 액세스 토큰 조회
	@POST("oauth2/v4/token")
	suspend fun getAccessToken(
		@Body request: String
	): Result<GoogleTokenResponse>
}