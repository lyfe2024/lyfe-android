package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.comment.CommentsResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentService {

	// 댓글 조회
	@GET("/v1/comments/latest")
	suspend fun fetchLatestComments(
		@Query("comment_board_id") boardId: Long,
		@Query("cursorId") lastCommentId: Long
	): Result<CommentsResponse>
}