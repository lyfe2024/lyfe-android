package com.lyfe.android.core.data.model.board

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateBoardRequest(
	@SerialName("title")
	val title: String,
	@SerialName("content")
	val content: String,
	@SerialName("imageUrl")
	val imageUrl: String
)