package com.lyfe.android.core.model

data class Comment(
	val id: Long = -1L,
	val content: String = "",
	val user: CommentUser = CommentUser(),
	val updatedAt: String = "",
	val replyList: List<Comment> = emptyList()
) {
	data class CommentUser(
		val id: Long = -1L,
		val username: String = "",
		val profileImg: String = ""
	)
}