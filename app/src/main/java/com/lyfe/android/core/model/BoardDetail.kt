package com.lyfe.android.core.model

data class BoardDetail(
	val id: Long = -1L,
	val user: User = User(),
	val title: String = "",
	val content: String = "",
	val topic: String = "",
	val imageUrl: String = "",
	val boardType: String = "",
	val whiskyCount: String = "0",
	val commentCount: String = "0",
	val updatedAt: String = "",
)