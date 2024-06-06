package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReissueTokenResult(
	val result: Token
) {

	@Serializable
	data class Token(
		val accessToken: String,
		val refreshToken: String
	)
}