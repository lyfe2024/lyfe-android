package com.lyfe.android.core.data.model.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponse(
	@SerialName("list") val commentList: List<CommentDto>
)