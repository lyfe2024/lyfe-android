package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
	val id: Long,
	val username: String = "",
	val profile: String = ""
)