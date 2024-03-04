package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReissueTokenRequest(
	val token: String
)