package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.comment.CommentsResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.CommentService
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
	private val commentService: CommentService
) : CommentDataSource {

	// 댓글 조회
	override suspend fun fetchLatestComments(boardId: Long, lastCommentId: Long): Result<CommentsResponse> {
		return commentService.fetchLatestComments(boardId = boardId, lastCommentId = lastCommentId)
	}
}