package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.model.GetBoardListResponse
import com.lyfe.android.core.data.model.GetUserBoardListResponse
import com.lyfe.android.core.data.network.model.Result

interface BoardDataSource {

	suspend fun getBoardDetail(
		boardId: Long
	): Result<GetBoardDetailResponse>

	suspend fun getLatestBoards(
		cursorId: Int?,
		date: String?,
		boardType: String?
	): Result<GetBoardListResponse>

	suspend fun getPopularBoards(
		whiskyCount: Int = 0,
		date: String?,
		boardType: String?
	): Result<GetBoardListResponse>

	suspend fun getUserBoards(
		userId: Long,
		boardType: String? = "BOARD",
		cursorId: Long?
	): Result<GetUserBoardListResponse>
}