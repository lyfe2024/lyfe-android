package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetUserBoardListResponse(
	val result: GetUserBoardResult
)

@Serializable
data class GetUserBoardResult(
	val boardPictureList: List<BoardPicture>,
	val page: PageInfo
)