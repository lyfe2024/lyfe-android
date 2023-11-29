package com.lyfe.android.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleTokenRequest(
	@SerialName("grant_type") val grantType: String,
	@SerialName("client_id") val clientId: String,
	@SerialName("client_secret") val clientSecret: String,
	@SerialName("code") val code: String
)