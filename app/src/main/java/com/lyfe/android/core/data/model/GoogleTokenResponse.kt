package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleTokenResponse(
	val access_token: String
)