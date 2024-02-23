package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PostUserRequest(
	val userToken: String,
	val nickname: String
)
