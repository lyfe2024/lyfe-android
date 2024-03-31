package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.BoardDataSource
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.model.GetBoardDetailResult
import com.lyfe.android.core.data.model.GetUserBoardResult
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.ApiResultException
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.AlbumRepository
import com.lyfe.android.core.domain.repository.BoardRepository
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.Page
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
		cursorId: Int?,
		date: String?,
		boardType: String?
	): Flow<List<Feed>> = flow {
		when (val response = boardDataSource.getLatestBoards(cursorId, date, boardType)) {
			is Result.Success -> {
				val result = response.body?.result ?: throw ApiResultException()
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
		whiskyCount: Int,
		date: String?,
		boardType: String?
	): Flow<List<Feed>> = flow {
		when (val response = boardDataSource.getPopularBoards(whiskyCount, date, boardType)) {
			is Result.Success -> {
				val result = response.body?.result ?: throw ApiResultException()
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
		userId: Long,
		boardType: String?,
		cursorId: Long?
	): Flow<Pair<List<Feed>, Page>> = flow {
		when (val response = boardDataSource.getUserBoards(userId, boardType, cursorId)) {
			is Result.Success -> {
				val result = response.body?.result ?: throw ApiResultException()
				emit(Pair(result.boardPictureList.map { it.toDomain() }, result.page.toDomain()))
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