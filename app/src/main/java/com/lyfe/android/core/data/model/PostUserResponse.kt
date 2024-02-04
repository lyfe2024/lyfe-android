package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostUserResponse(
	val result: PostUserResult
)

@Serializable
data class PostUserResult(
	val accessToken: String,
	val refreshToken: String
)