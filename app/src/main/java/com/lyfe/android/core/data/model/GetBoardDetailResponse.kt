package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetBoardDetailResponse(
	val result: BoardDetail
)

@Serializable
data class BoardDetail(
	val id: Long,
	val user: UserInfo,
	val title: String,
	val content: String,
	val topic: String,
	val imageUrl: String?,
	val boardType: String,
	val whiskyCount: Int,
	val commentCount: Int,
	val updatedAt: String
)