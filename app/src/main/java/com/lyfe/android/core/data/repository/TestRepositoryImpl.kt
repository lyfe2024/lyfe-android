package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.TestDataSource
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.TestRepository
import com.lyfe.android.core.model.TestResponse
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
	private val testDataSource: TestDataSource,
) : TestRepository {

	override suspend fun testApi(): Result<TestResponse> {
		return testDataSource.testApi()
	}
}