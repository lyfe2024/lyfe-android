package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetBoardListResponse(
	val result: GetBoardListResult
)

@Serializable
data class GetBoardListResult(
	val list: List<BoardDetail>
)