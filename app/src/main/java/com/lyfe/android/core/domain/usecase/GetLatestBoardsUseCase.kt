package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.BoardRepository
import javax.inject.Inject

class GetLatestBoardsUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {

	operator fun invoke(
		cursorId: Int?,
		date: String?,
		boardType: String?
	) = boardRepository.getLatestBoards(cursorId, date, boardType)
}