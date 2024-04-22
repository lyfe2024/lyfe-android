package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthUserResult(
	val userToken: String = "",
	val accessToken: String = "",
	val refreshToken: String = ""
)