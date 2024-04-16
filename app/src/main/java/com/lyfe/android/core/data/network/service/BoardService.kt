package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.GetBoardDetailResponse
import com.lyfe.android.core.data.model.GetBoardListResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
	// 글 상세 조회
	@GET("/v1/boards/details/{boardId}")
	suspend fun getBoardDetail(
		@Path("boardId") boardId: Long
	): Result<GetBoardDetailResponse>

	// 글 리스트 조회(최신순)
	@GET("/v1/boards/latest")
	suspend fun getLatestBoards(
		@Query("cursorId") cursorId: Int = 0,
		@Query("type") boardType: String? = "BOARD"
	): Result<GetBoardListResponse>

	// 글 리스트 조회(인기순)
	@GET("/v1/boards/popular")
	suspend fun getPopularBoards(
		@Query("cursorId") cursorId: Int = 0,
		@Query("type") boardType: String? = "BOARD",
		@Query("popularType") popularType: String = "WHISKY"
	): Result<GetBoardListResponse>

	// 자신이 작성한 글 조회
	@GET("/v1/boards/me")
	suspend fun getUserBoards(
		@Query("type") boardType: String? = "BOARD",
		@Query("cursorId") cursorId: Long
	): Result<GetBoardListResponse>
}