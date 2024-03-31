package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.BoardRepository
import com.lyfe.android.core.domain.repository.UserRepository
import javax.inject.Inject

class GetUserBoardUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {

	operator fun invoke(
		userId: Long,
		boardType: String?,
		cursorId: Long?
	) = boardRepository.getUserBoards(userId, boardType, cursorId)
}