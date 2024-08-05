package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

	// 댓글 조회
	fun fetchLatestComments(
		boardId: Long
	): Flow<Result<List<Comment>>>

	// 댓글 생성
	fun createComment(
		commentBoardId: Long,
		content: String,
		commentGroupId: Long? = null
	): Flow<Result<Long>>

	// 댓글 수정
	fun updateComment(
		commentId: Long,
		content: String
	): Flow<Result<Long>>
}