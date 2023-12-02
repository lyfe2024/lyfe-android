package com.lyfe.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class TestResponse(
	val result: TestResult
)