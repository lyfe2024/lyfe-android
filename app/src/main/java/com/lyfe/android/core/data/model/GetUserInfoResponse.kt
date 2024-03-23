package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetUserInfoResponse(
	val result: UserInfo
)