package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetBoardListResult
import com.lyfe.android.core.data.model.board.BoardDetailDto
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.FeedType

interface BoardDataSource {

	suspend fun fetchBoardDetail(
		boardId: Long
	): Result<BoardDetailDto>

	suspend fun getLatestBoards(
		cursorId: Long,
		boardType: String = FeedType.BOARD.name
	): Result<GetBoardListResult>

	suspend fun getPopularBoards(
		cursorId: Long = 0,
		boardType: String = FeedType.BOARD.name,
		popularType: String
	): Result<GetBoardListResult>

	suspend fun getUserBoards(
		boardType: String = FeedType.BOARD.name,
		cursorId: Long
	): Result<GetBoardListResult>
}