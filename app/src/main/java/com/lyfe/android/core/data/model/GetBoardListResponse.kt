package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetBoardListResponse(
	val result: List<GetBoardDetailResult>
)