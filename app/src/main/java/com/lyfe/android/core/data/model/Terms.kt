package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Terms(
	val title: String,
	val content: String,
	val version: String,
	val policyType: String
)