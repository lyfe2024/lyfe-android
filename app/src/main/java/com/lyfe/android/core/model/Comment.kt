package com.lyfe.android.core.model

data class Comment(
	val id: Long,
	val profileImg: String,
	val userName: String,
	val date: String,
	val content: String,
	val replyCommentList: List<Comment>
)