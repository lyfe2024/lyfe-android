package com.lyfe.android.core.data.model.board

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardDetailDto(
	@SerialName("id") val id: Long,
	@SerialName("user") val user: UserDto?,
	@SerialName("title") val title: String?,
	@SerialName("content") val content: String?,
	@SerialName("topic") val topic: String?,
	@SerialName("imageUrl") val imageUrl: String?,
	@SerialName("boardType") val boardType: String?,
	@SerialName("whiskyCount") val whiskyCount: String?,
	@SerialName("commentCount") val commentCount: String?,
	@SerialName("updatedAt") val updatedAt: String?,
) {
	@Serializable
	data class UserDto(
		@SerialName("id") val id: Long,
		@SerialName("username") val username: String?,
		@SerialName("profile") val profile: String?,
	)
}