package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
	private val commentRepository: CommentRepository
) {

	// 댓글 조회
	operator fun invoke(
		boardId: Long,
		lastCommentId: Long
	) = commentRepository.fetchLatestComments(
		boardId = boardId,
		lastCommentId = lastCommentId
	)
}