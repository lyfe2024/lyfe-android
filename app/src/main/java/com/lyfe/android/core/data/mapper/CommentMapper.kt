package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.comment.CommentDto
import com.lyfe.android.core.model.Comment

internal fun CommentDto.toDomain() = Comment(
	id = this.id,
	content = this.content ?: "",
	user = this.user?.toDomain() ?: Comment.CommentUser(),
	updatedAt = this.updatedAt ?: "",
	replyList = this.replyList?.map {
		Comment(
			id = this.id,
			content = this.content ?: "",
			user = this.user?.toDomain() ?: Comment.CommentUser(),
			updatedAt = this.updatedAt ?: ""
		)
	} ?: emptyList()
)

internal fun CommentDto.CommentDtoUser.toDomain() = Comment.CommentUser(
	id = this.id,
	username = this.username ?: "",
	profileImg = this.profileImg ?: ""
)