package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.BoardRepository
import javax.inject.Inject

class GetUserBoardUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {

	operator fun invoke(
		boardType: String?,
		cursorId: Long = 0
	) = boardRepository.getUserBoards(boardType, cursorId)
}