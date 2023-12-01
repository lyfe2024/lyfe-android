package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.network.di.NetworkModule
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.TestService
import com.lyfe.android.core.model.TestResponse
import javax.inject.Inject

class TestDataSourceImpl @Inject constructor() : TestDataSource {

	private val okHttpClient = NetworkModule.providesLyfeOkHttpClient()
	private val retrofit = NetworkModule.providesLyfeRetrofit(okHttpClient)

	private val testService = retrofit.create(TestService::class.java)

	override suspend fun testApi(): Result<TestResponse> {
		return runCatching {
			testService.testApi()
		}.getOrElse { e ->
			Result.Unexpected(e)
		}
	}
}