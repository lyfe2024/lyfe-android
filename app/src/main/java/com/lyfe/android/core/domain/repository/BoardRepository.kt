package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.Page
import kotlinx.coroutines.flow.Flow

interface BoardRepository {

	suspend fun getBoardDetail(boardId: Long): Result<GetBoardDetailResponse>

	fun getLatestBoards(
		cursorId: Int,
		boardType: String
	): Flow<List<Feed>>

	fun getPopularBoards(
		cursorId: Int = 0,
		boardType: String?,
		popularType: String
	): Flow<List<Feed>>

	fun getUserBoards(
		boardType: String? = "BOARD",
		cursorId: Long
	): Flow<List<Feed>>
}