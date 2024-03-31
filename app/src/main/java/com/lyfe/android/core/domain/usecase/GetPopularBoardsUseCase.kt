package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.BoardRepository
import javax.inject.Inject

class GetPopularBoardsUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {

	operator fun invoke(
		whiskyCount: Int = 0,
		date: String?,
		boardType: String?
	) = boardRepository.getPopularBoards(whiskyCount, date, boardType)
}