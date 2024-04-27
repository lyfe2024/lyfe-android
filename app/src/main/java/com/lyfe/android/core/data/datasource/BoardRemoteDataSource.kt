package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.model.GetBoardListResult
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.BoardService
import javax.inject.Inject

class BoardRemoteDataSource @Inject constructor(
	private val boardService: BoardService
) : BoardDataSource {

	override suspend fun getBoardDetail(boardId: Long): Result<GetBoardDetailResponse> {
		return boardService.getBoardDetail(boardId = boardId)
	}

	override suspend fun getLatestBoards(
		cursorId: Long,
		boardType: String
	): Result<GetBoardListResult> {
		return boardService.getLatestBoards(
			cursorId = cursorId,
			boardType = boardType
		)
	}

	override suspend fun getPopularBoards(
		cursorId: Long,
		boardType: String,
		popularType: String
	): Result<GetBoardListResult> {
		return boardService.getPopularBoards(
			cursorId = cursorId,
			boardType = boardType
		)
	}

	override suspend fun getUserBoards(
		boardType: String,
		cursorId: Long
	): Result<GetBoardListResult> {
		return boardService.getUserBoards(
			boardType = boardType,
			cursorId = cursorId
		)
	}
}