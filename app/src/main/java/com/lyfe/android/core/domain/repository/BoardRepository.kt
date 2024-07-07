package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.BoardDetail
import kotlinx.coroutines.flow.Flow

interface BoardRepository {

	// 글 상세 조회
	fun fetchBoardDetail(
		boardId: Long
	): Flow<Result<BoardDetail>>

	fun getLatestBoards(
		cursorId: Long,
		boardType: String
	): Flow<List<Feed>>

	fun getPopularBoards(
		cursorId: Long = 0,
		boardType: String,
		popularType: String,
		date: String? = null
	): Flow<List<Feed>>

	fun getUserBoards(
		boardType: String,
		cursorId: Long
	): Flow<List<Feed>>
}