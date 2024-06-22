package com.lyfe.android.core.model.board

import com.lyfe.android.core.model.FeedType

data class RegisterBoardRequestData(
	val title: String,
	val content: String,
	val boardType: FeedType,
	val userId: Long,
	val topicId: Long
)