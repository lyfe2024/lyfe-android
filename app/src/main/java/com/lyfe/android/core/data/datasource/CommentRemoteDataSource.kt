package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.comment.CommentsResponse
import com.lyfe.android.core.data.model.comment.CreateCommentRequest
import com.lyfe.android.core.data.model.comment.CreateCommentResponse
import com.lyfe.android.core.data.model.comment.UpdateCommentRequest
import com.lyfe.android.core.data.model.comment.UpdateCommentResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.CommentService
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
	private val commentService: CommentService
) : CommentDataSource {

	// 댓글 조회
	override suspend fun fetchLatestComments(boardId: Long): Result<CommentsResponse> {
		return commentService.fetchLatestComments(boardId = boardId)
	}

	// 댓글 생성
	override suspend fun createComment(
		commentBoardId: Long,
		content: String,
		commentGroupId: Long?
	): Result<CreateCommentResponse> {
		return commentService.createComment(
			commentBoardId = commentBoardId,
			request = CreateCommentRequest(
				content = content,
				commentGroupId = commentGroupId
			)
		)
	}

	// 댓글 수정
	override suspend fun updateComment(
		commentId: Long,
		content: String
	): Result<UpdateCommentResponse> {
		return commentService.updateComment(
			commentId = commentId,
			request = UpdateCommentRequest(content)
		)
	}

}