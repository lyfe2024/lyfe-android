package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.model.GetBoardListResponse
import com.lyfe.android.core.data.network.model.Result

interface BoardDataSource {

	suspend fun getBoardDetail(
		boardId: Long
	): Result<GetBoardDetailResponse>

	suspend fun getLatestBoards(
		cursorId: Int,
		boardType: String
	): Result<GetBoardListResponse>

	suspend fun getPopularBoards(
		cursorId: Int = 0,
		boardType: String?,
		popularType: String
	): Result<GetBoardListResponse>

	suspend fun getUserBoards(
		boardType: String? = "BOARD",
		cursorId: Long
	): Result<GetBoardListResponse>
}