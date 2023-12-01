package com.lyfe.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class TestResponse(
	val isSuccess: Boolean,
	val message: String,
	val code: Int,
	val timestamp: String,
	val result: List<TestResult>
)