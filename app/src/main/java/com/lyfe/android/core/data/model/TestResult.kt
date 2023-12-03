package com.lyfe.android.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestResult(
	@SerialName("url") val url: String,
	@SerialName("key") val key: String,
	@SerialName("expiresAt") val expiresAt: String
)