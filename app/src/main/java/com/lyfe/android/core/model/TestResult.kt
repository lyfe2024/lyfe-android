package com.lyfe.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class TestResult(
	val url: String,
	val key: String,
	val expiresAt: String
)