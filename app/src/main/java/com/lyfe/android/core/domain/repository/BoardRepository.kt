package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.Page
import kotlinx.coroutines.flow.Flow

interface BoardRepository {

	suspend fun getBoardDetail(boardId: Long): Result<GetBoardDetailResponse>

	fun getLatestBoards(
		cursorId: Int?,
		date: String?,
		boardType: String?
	): Flow<List<Feed>>

	fun getPopularBoards(
		whiskyCount: Int = 0,
		date: String?,
		boardType: String?
	): Flow<List<Feed>>

	fun getUserBoards(
		boardType: String? = "BOARD",
		cursorId: Long
	): Flow<List<Feed>>
}