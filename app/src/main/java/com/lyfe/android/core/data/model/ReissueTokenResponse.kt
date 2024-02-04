package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReissueTokenResponse(
	val result: ReissueTokenResult
)

@Serializable
data class ReissueTokenResult(
	val accessToken: String,
	val refreshToken: String
)
