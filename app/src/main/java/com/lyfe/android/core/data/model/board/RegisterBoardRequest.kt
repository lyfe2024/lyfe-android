package com.lyfe.android.core.data.model.board

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterBoardRequest(
	@SerialName("title")
	val title: String,
	@SerialName("content")
	val content: String,
	@SerialName("boardType")
	val boardType: String,
	@SerialName("userId")
	val userId: Long,
	@SerialName("topicId")
	val topicId: Long
)