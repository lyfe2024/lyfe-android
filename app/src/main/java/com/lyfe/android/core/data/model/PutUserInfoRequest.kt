package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PutUserInfoRequest(
	val nickname: String,
	val profileUrl: String,
	val width: Int,
	val height: Int
)