package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetBoardListResult
import com.lyfe.android.core.data.model.board.RegisterBoardRequest
import com.lyfe.android.core.data.model.board.RegisterBoardResponse
import com.lyfe.android.core.data.model.board.UpdateBoardRequest
import com.lyfe.android.core.data.model.board.UpdateBoardResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.BoardService
import javax.inject.Inject

class BoardRemoteDataSource @Inject constructor(
	private val boardService: BoardService
) : BoardDataSource {

	// 글 상세 조회
	override suspend fun fetchBoardDetail(
		boardId: Long
	) = boardService.fetchBoardDetail(boardId = boardId)

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
		popularType: String,
		date: String?
	): Result<GetBoardListResult> {
		return boardService.getPopularBoards(
			cursorId = cursorId,
			boardType = boardType,
			popularType = popularType,
			date = date
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

	override suspend fun registerBoard(
		boardRequest: RegisterBoardRequest
	): Result<RegisterBoardResponse> {
		return boardService.registerBoard(boardRequest)
	}

	override suspend fun updateBoard(
		boardId: Long,
		boardRequest: UpdateBoardRequest
	): Result<UpdateBoardResponse> {
		return boardService.updateBoard(boardId = boardId, boardRequest = boardRequest)
	}
}