package com.lyfe.android.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleTokenResponse(
	@SerialName("access_token") val accessToken: String
)