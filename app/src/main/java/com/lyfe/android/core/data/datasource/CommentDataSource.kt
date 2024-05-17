package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.comment.CommentsResponse
import com.lyfe.android.core.data.network.model.Result

interface CommentDataSource {

	// 댓글 조회
	suspend fun fetchLatestComments(
		boardId: Long,
		lastCommentId: Long
	): Result<CommentsResponse>
}