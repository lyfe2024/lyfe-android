package com.lyfe.android.core.domain.usecase.comment

import com.lyfe.android.core.domain.repository.CommentRepository
import javax.inject.Inject

class UpdateCommentUseCase @Inject constructor(
	private val commentRepository: CommentRepository
) {

	operator fun invoke(commentId: Long, content: String) =
		commentRepository.updateComment(commentId = commentId, content = content)
}