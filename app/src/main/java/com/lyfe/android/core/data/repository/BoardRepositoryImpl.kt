package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.BoardDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.ApiResultException
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.model.transform
import com.lyfe.android.core.domain.repository.BoardRepository
import com.lyfe.android.core.model.Feed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
	private val boardDataSource: BoardDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : BoardRepository {

	override fun fetchBoardDetail(
		boardId: Long
	) = flow {
		emit(
			boardDataSource.fetchBoardDetail(
				boardId = boardId
			).transform { it.toDomain() }
		)
	}.flowOn(ioDispatcher)

	override fun getLatestBoards(
		cursorId: Long,
		boardType: String
	): Flow<List<Feed>> = flow {
		when (
			val response = boardDataSource.getLatestBoards(
				cursorId = cursorId,
				boardType = boardType
			)
		) {
			is Result.Success -> {
				val result = response.body.list
				emit(result.map { it.toDomain() })
			}

			is Result.Failure -> {
				throw ApiResultException(response.error)
			}

			is Result.NetworkError -> {
				throw response.exception
			}

			is Result.Unexpected -> {
				throw response.t
			}
		}
	}.flowOn(ioDispatcher)

	override fun getPopularBoards(
		cursorId: Long,
		boardType: String,
		popularType: String
	): Flow<List<Feed>> = flow {
		when (
			val response = boardDataSource.getPopularBoards(
				cursorId = cursorId,
				boardType = boardType,
				popularType = popularType
			)
		) {
			is Result.Success -> {
				val result = response.body.list
				emit(result.map { it.toDomain() })
			}

			is Result.Failure -> {
				throw ApiResultException(response.error)
			}

			is Result.NetworkError -> {
				throw response.exception
			}

			is Result.Unexpected -> {
				throw response.t
			}
		}
	}.flowOn(ioDispatcher)

	override fun getUserBoards(
		boardType: String,
		cursorId: Long
	): Flow<List<Feed>> = flow {
		when (
			val response = boardDataSource.getUserBoards(
				boardType = boardType,
				cursorId = cursorId
			)
		) {
			is Result.Success -> {
				val result = response.body.list
				emit(result.map { it.toDomain() })
			}

			is Result.Failure -> {
				throw ApiResultException(response.error)
			}

			is Result.NetworkError -> {
				throw response.exception
			}

			is Result.Unexpected -> {
				throw response.t
			}
		}
	}.flowOn(ioDispatcher)
}