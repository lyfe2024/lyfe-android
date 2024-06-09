package com.lyfe.android.core.domain.usecase.board

import com.lyfe.android.core.domain.repository.BoardRepository
import com.lyfe.android.core.model.board.UpdateBoardRequestData
import javax.inject.Inject

class UpdateBoardUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {

	operator fun invoke(
		boardId: Long,
		updateBoardRequestData: UpdateBoardRequestData
	) = boardRepository.updateBoard(
		boardId = boardId,
		updateBoardRequestData = updateBoardRequestData
	)
}