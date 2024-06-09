package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.board.UpdateBoardRequest
import com.lyfe.android.core.data.model.board.UpdateBoardResponse
import com.lyfe.android.core.model.board.UpdateBoardData
import com.lyfe.android.core.model.board.UpdateBoardRequestData

internal fun UpdateBoardRequestData.toRequest() = UpdateBoardRequest(
	title = this.title,
	content = this.content,
	imageUrl = this.imageUrl
)

internal fun UpdateBoardResponse.toDomain() = UpdateBoardData(
	id = this.id
)