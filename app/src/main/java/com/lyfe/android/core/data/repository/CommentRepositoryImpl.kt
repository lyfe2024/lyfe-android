package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.CommentDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.model.comment.CreateCommentResponse
import com.lyfe.android.core.data.model.comment.UpdateCommentResponse
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.model.transform
import com.lyfe.android.core.domain.repository.CommentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
	private val commentDataSource: CommentDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CommentRepository {

	override fun fetchLatestComments(boardId: Long) = flow {
		val result = commentDataSource.fetchLatestComments(boardId).transform {
			it.commentList.map { it.toDomain() }
		}

		emit(result)
	}.flowOn(ioDispatcher)

	override fun createComment(
		commentBoardId: Long,
		content: String,
		commentGroupId: Long?
	) = flow {
		val result = commentDataSource.createComment(
			commentBoardId = commentBoardId,
			content = content,
			commentGroupId = commentGroupId
		).transform {
			it.id
		}

		emit(result)
	}.flowOn(ioDispatcher)

	override fun updateComment(commentId: Long, content: String) = flow {
		val result = commentDataSource.updateComment(
			commentId = commentId,
			content =content
		).transform {
			it.id
		}

		emit(result)
	}.flowOn(ioDispatcher)
}