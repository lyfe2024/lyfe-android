package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.TestService
import com.lyfe.android.core.data.model.TestResponse
import javax.inject.Inject

class TestDataSourceImpl @Inject constructor(
	private val testService: TestService
) : TestDataSource {

	override suspend fun fetchTestData(): Result<TestResponse> {
		return testService.fetchTestData()
	}
}