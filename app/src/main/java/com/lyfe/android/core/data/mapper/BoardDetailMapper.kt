package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.board.BoardDetailDto
import com.lyfe.android.core.model.BoardDetail
import com.lyfe.android.core.model.User

internal fun BoardDetailDto.toDomain() = BoardDetail(
	id = this.id,
	user = this.user?.toDomain() ?: User(),
	title = this.title ?: "",
	content = this.content ?: "",
	topic = this.topic ?: "",
	imageUrl = this.imageUrl ?: "",
	boardType = this.boardType ?: "",
	whiskyCount = this.whiskyCount ?: "",
	commentCount = this.commentCount ?: "",
	updatedAt = this.updatedAt ?: ""
)

internal fun BoardDetailDto.UserDto.toDomain() = User(
	id = this.id,
	name = this.username ?: "",
	profileImage = this.profile ?: ""
)