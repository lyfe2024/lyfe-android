package com.lyfe.android.core.data.model.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCommentRequest(
	@SerialName("content")
	val content: String
)