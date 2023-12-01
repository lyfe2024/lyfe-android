package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.TestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {
	// 테스트 API 조회
	@GET("/v2/villages")
	suspend fun testApi(
		@Query("page") page: Int = 0,
		@Query("pageSize") pageSize: Int = 5
	): Result<TestResponse>
}