package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.model.TestResponse

interface TestDataSource {
	suspend fun fetchTestData(): Result<TestResponse>
}