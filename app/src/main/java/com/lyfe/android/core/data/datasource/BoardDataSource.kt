package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.model.GetBoardListResult
import com.lyfe.android.core.data.network.model.Result

interface BoardDataSource {

	suspend fun getBoardDetail(
		boardId: Long
	): Result<GetBoardDetailResponse>

	suspend fun getLatestBoards(
		cursorId: Long,
		boardType: String
	): Result<GetBoardListResult>

	suspend fun getPopularBoards(
		cursorId: Long = 0,
		boardType: String?,
		popularType: String
	): Result<GetBoardListResult>

	suspend fun getUserBoards(
		boardType: String? = "BOARD",
		cursorId: Long
	): Result<GetBoardListResult>
}