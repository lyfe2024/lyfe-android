package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.GetBoardListResult
import com.lyfe.android.core.data.model.board.BoardDetailDto
import com.lyfe.android.core.data.model.board.RegisterBoardRequest
import com.lyfe.android.core.data.model.board.RegisterBoardResponse
import com.lyfe.android.core.data.model.board.UpdateBoardRequest
import com.lyfe.android.core.data.model.board.UpdateBoardResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {

	// 글 상세 조회
	@GET("/v1/boards/detail/{boardId}")
	suspend fun fetchBoardDetail(
		@Path("boardId") boardId: Long
	): Result<BoardDetailDto>

	// 글 리스트 조회(최신순)
	@GET("/v1/boards/latest")
	suspend fun getLatestBoards(
		@Query("cursorId") cursorId: Long = 0,
		@Query("type") boardType: String = "BOARD"
	): Result<GetBoardListResult>

	// 글 리스트 조회(인기순)
	@GET("/v1/boards/popular")
	suspend fun getPopularBoards(
		@Query("cursorId") cursorId: Long = 0,
		@Query("type") boardType: String = "BOARD",
		@Query("popularType") popularType: String = "WHISKY",
		@Query("date") date: String? = null
	): Result<GetBoardListResult>

	// 자신이 작성한 글 조회
	@GET("/v1/boards/me")
	suspend fun getUserBoards(
		@Query("type") boardType: String = "BOARD",
		@Query("cursorId") cursorId: Long
	): Result<GetBoardListResult>

	@POST("/v1/boards")
	suspend fun registerBoard(
		@Body boardRequest: RegisterBoardRequest
	): Result<RegisterBoardResponse>

	@PUT("/v1/boards/{boardId}")
	suspend fun updateBoard(
		@Path("boardId") boardId: Long,
		@Body boardRequest: UpdateBoardRequest
	): Result<UpdateBoardResponse>
}