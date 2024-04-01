package com.lyfe.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ServicePolicyResult(
	val title: String = "",
	val content: String = "",
	val version: String = "",
	val policyType: String = ""
)