package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthUserResponse(
	val result: AuthUserResult
)

@Serializable
data class AuthUserResult(
	val sample: String
)
