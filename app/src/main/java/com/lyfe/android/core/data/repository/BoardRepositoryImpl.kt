package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.BoardDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.ApiResultException
import com.lyfe.android.core.data.network.model.Result
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

	override suspend fun getBoardDetail(boardId: Long): Result<GetBoardDetailResponse> {
		return boardDataSource.getBoardDetail(boardId = boardId)
	}

	override fun getLatestBoards(
		cursorId: Int,
		boardType: String
	): Flow<List<Feed>> = flow {
		when (val response = boardDataSource.getLatestBoards(cursorId, boardType)) {
			is Result.Success -> {
				val result = response.body?.result?.list ?: throw ApiResultException()
				emit(result.map { it.toDomain() })
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t ?: ApiResultException()
			}
		}
	}.flowOn(ioDispatcher)

	override fun getPopularBoards(
		cursorId: Int,
		boardType: String?,
		popularType: String
	): Flow<List<Feed>> = flow {
		when (val response = boardDataSource.getPopularBoards(cursorId, boardType, popularType)) {
			is Result.Success -> {
				val result = response.body?.result?.list ?: throw ApiResultException()
				emit(result.map { it.toDomain() })
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t ?: ApiResultException()
			}
		}
	}.flowOn(ioDispatcher)

	override fun getUserBoards(
		boardType: String?,
		cursorId: Long
	): Flow<List<Feed>> = flow {
		when (val response = boardDataSource.getUserBoards(boardType, cursorId)) {
			is Result.Success -> {
				val result = response.body?.result?.list ?: throw ApiResultException()
				emit(result.map { it.toDomain() })
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t ?: ApiResultException()
			}
		}
	}.flowOn(ioDispatcher)
}