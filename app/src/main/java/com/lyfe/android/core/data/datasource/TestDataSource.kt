package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.TestResponse

interface TestDataSource {
	suspend fun testApi(): Result<TestResponse>
}