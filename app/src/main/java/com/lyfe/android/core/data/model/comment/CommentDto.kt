package com.lyfe.android.core.data.model.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
	@SerialName("id") val id: Long,
	@SerialName("content") val content: String?,
	@SerialName("commentGroupId") val commentGroupId: Long?,
	@SerialName("user") val user: CommentDtoUser?,
	@SerialName("updatedAt") val updatedAt: String?,
	@SerialName("replies") val replyList: List<CommentDto>? = emptyList()
) {
	@Serializable
	data class CommentDtoUser (
		@SerialName("id") val id: Long,
		@SerialName("username") val username: String?,
		@SerialName("profile") val profileImg: String?,
	)
}