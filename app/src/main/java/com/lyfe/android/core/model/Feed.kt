package com.lyfe.android.core.model

data class Feed(
	val feedId: Long,
	val title: String,
	val content: String,
	val feedImageUrl: String,
	val date: String,
	val userId: Long,
	val userName: String,
	val userProfileImgUrl: String,
	val whiskyCount: Int,
	val commentCount: Int,
	val isLike: Boolean
)