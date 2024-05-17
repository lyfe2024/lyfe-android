package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.BoardRepository
import javax.inject.Inject

class GetBoardDetailUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {
	operator fun invoke(
		boardId: Long
	) = boardRepository.fetchBoardDetail(
		boardId = boardId
	)
}