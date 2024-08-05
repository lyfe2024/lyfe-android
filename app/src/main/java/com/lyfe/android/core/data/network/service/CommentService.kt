package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.comment.CommentsResponse
import com.lyfe.android.core.data.model.comment.CreateCommentRequest
import com.lyfe.android.core.data.model.comment.CreateCommentResponse
import com.lyfe.android.core.data.model.comment.UpdateCommentRequest
import com.lyfe.android.core.data.model.comment.UpdateCommentResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {

	// 댓글 조회
	@GET("/v1/comments/latest")
	suspend fun fetchLatestComments(
		@Query("comment_board_id") boardId: Long,
	): Result<CommentsResponse>

	// 댓글 생성
	@POST("/v1/comments")
	suspend fun createComment(
		@Query("comment_board_id") commentBoardId: Long,
		@Body request: CreateCommentRequest
	): Result<CreateCommentResponse>

	// 댓글 수정
	@PUT("/v1/comments/{commentId}")
	suspend fun updateComment(
		@Path("commentId") commentId: Long,
		@Body request: UpdateCommentRequest
	): Result<UpdateCommentResponse>
}