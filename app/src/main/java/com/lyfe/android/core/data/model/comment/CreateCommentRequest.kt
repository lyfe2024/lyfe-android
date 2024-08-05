package com.lyfe.android.core.data.model.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentRequest(
	@SerialName("content")
	val content: String,
	@SerialName("commentGroupId")
	val commentGroupId: Long? = null
)