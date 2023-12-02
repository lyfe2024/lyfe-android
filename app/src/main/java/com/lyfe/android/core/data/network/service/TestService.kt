package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.TestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {
	// 테스트 API 조회
	@GET("/v1/images/get-upload-url")
	suspend fun testApi(
		@Query("format") format: String = "jpg",
		@Query("path") path: String = "topic_picture"
	): Result<TestResponse>
}