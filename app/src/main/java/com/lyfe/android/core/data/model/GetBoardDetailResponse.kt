package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

//"result": {
//	"id": 1,
//	"user": {
//		"id": 1,
//		"username": "홍길동",
//		"profile": "https://picsum.photos/700/700"
//	},
//	"title": "타이틀",
//	"content": "컨텐츠",
//	"boardType": "BOARD_CONTENT",
//	"whiskyCount": 1,
//	"commentCount": 1,
//	"updatedAt": "2021-01-01"
//}
@Serializable
data class GetBoardDetailResponse(
	val result: GetBoardDetailResult
)

@Serializable
data class GetBoardDetailResult(
	val id: Long,
	val user: UserInfo,
	val title: String,
	val content: String,
	val boardType: String,
	val whiskyCount: Int,
	val commentCount: Int,
	val updatedAt: String
)