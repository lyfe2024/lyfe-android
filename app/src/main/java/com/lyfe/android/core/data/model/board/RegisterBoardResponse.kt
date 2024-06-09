package com.lyfe.android.core.data.model.board

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterBoardResponse(
	@SerialName("id")
	val id: Long
)