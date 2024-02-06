package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameResponse(
	val result: CheckNicknameResult
)

@Serializable
data class CheckNicknameResult(
	val isAvailable: Boolean
)