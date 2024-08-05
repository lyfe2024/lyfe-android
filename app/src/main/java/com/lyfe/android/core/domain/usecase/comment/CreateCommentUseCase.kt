package com.lyfe.android.core.domain.usecase.comment

import com.lyfe.android.core.domain.repository.CommentRepository
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
	private val commentRepository: CommentRepository
) {
	operator fun invoke(
		commentBoardId: Long,
		content: String,
		commentGroupId: Long? = null
	) = commentRepository.createComment(
		commentBoardId = commentBoardId,
		content = content,
		commentGroupId = commentGroupId
	)
}