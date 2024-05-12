package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

	// 댓글 조회
	fun fetchLatestComments(
		boardId: Long,
		lastCommentId: Long,
	): Flow<Result<List<Comment>>>
}