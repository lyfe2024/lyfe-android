package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.TestResponse

interface TestRepository {
	suspend fun testApi(): Result<TestResponse>
}