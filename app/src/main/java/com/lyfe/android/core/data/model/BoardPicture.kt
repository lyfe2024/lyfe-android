package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BoardPicture(
	val id: Long,
	val title: String,
	val picture: Picture,
	val date: String,
	val boardType: String,
	val user: UserInfo,
	val whiskyCount: Int,
	val commentCount: Int,
	val isLike: Boolean = false
)

@Serializable
data class Picture(
	val height: Int,
	val width: Int,
	val pictureUrl: String
)
