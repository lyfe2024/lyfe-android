package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.model.TestResponse

interface TestRepository {
	suspend fun fetchTestData(): Result<TestResponse>
}