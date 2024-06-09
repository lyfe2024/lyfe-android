package com.lyfe.android.core.data.model.board

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateBoardResponse(
	@SerialName("id")
	val id: Long
)