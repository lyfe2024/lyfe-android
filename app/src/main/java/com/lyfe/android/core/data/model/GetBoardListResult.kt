package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetBoardListResult(
	val list: List<BoardDetail>
)