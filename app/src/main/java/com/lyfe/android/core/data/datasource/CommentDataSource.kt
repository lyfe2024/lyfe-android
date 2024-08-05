package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.comment.CommentsResponse
import com.lyfe.android.core.data.model.comment.CreateCommentResponse
import com.lyfe.android.core.data.model.comment.UpdateCommentResponse
import com.lyfe.android.core.data.network.model.Result

interface CommentDataSource {

	// 댓글 조회
	suspend fun fetchLatestComments(
		boardId: Long
	): Result<CommentsResponse>

	// 댓글 생성
	suspend fun createComment(
		commentBoardId: Long,
		content: String,
		commentGroupId: Long? = null
	): Result<CreateCommentResponse>

	// 댓글 수정
	suspend fun updateComment(
		commentId: Long,
		content: String
	): Result<UpdateCommentResponse>
}