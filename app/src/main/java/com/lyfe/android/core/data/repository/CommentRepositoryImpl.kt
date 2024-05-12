package com.lyfe.android.core.data.repository

import android.util.Log
import com.lyfe.android.core.data.datasource.CommentDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.onSuccess
import com.lyfe.android.core.data.network.model.transform
import com.lyfe.android.core.domain.repository.CommentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
	private val commentDataSource: CommentDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CommentRepository {

	override fun fetchLatestComments(
		boardId: Long,
		lastCommentId: Long
	) = flow {
		val result = commentDataSource.fetchLatestComments(
			boardId = boardId,
			lastCommentId = lastCommentId
		).transform {
			it.commentList.map { it.toDomain() }
		}

		emit(result)
	}.flowOn(ioDispatcher)
}