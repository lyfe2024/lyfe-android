package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.model.GetBoardListResponse
import com.lyfe.android.core.data.model.GetUserBoardListResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.BoardService
import javax.inject.Inject

class BoardDataSourceImpl @Inject constructor(
	private val boardService: BoardService
) : BoardDataSource {

	override suspend fun getBoardDetail(boardId: Long): Result<GetBoardDetailResponse> {
		return boardService.getBoardDetail(boardId = boardId)
	}

	override suspend fun getLatestBoards(
		cursorId: Int?,
		date: String?,
		boardType: String?
	): Result<GetBoardListResponse> {
		return boardService.getLatestBoards(
			cursorId = cursorId,
			date = date,
			boardType = boardType
		)
	}

	override suspend fun getPopularBoards(
		whiskyCount: Int,
		date: String?,
		boardType: String?
	): Result<GetBoardListResponse> {
		return boardService.getPopularBoards(
			whiskyCount = whiskyCount,
			date = date,
			boardType = boardType
		)
	}

	override suspend fun getUserBoards(
		userId: Long,
		boardType: String?,
		cursorId: Long?
	): Result<GetUserBoardListResponse> {
		return boardService.getUserBoards(
			userId = userId,
			boardType = boardType,
			cursorId = cursorId
		)
	}
}