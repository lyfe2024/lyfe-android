package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.BoardRepository
import javax.inject.Inject

class GetPopularBoardsUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {

	operator fun invoke(
		cursorId: Long = 0,
		boardType: String,
		popularType: String
	) = boardRepository.getPopularBoards(
		cursorId = cursorId,
		boardType = boardType,
		popularType = popularType
	)
}