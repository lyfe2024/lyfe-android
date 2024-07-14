package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.board.RegisterBoardRequest
import com.lyfe.android.core.data.model.board.RegisterBoardResponse
import com.lyfe.android.core.model.board.RegisterBoardData
import com.lyfe.android.core.model.board.RegisterBoardRequestData

internal fun RegisterBoardRequestData.toRequest() = RegisterBoardRequest(
	title = this.title,
	content = this.content,
	boardType = this.boardType.name,
	userId = this.userId,
	topicId = this.topicId
)

internal fun RegisterBoardResponse.toDomain() = RegisterBoardData(
	id = this.id
)