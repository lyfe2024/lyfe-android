package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.BoardRepository
import com.lyfe.android.core.model.FeedType
import com.lyfe.android.feature.feed.model.FeedSortType
import javax.inject.Inject

class GetImageBoardsUseCase @Inject constructor(
	private val boardRepository: BoardRepository
) {

	operator fun invoke(
		cursorId: Long,
		sortType: FeedSortType
	) = when (sortType) {
		FeedSortType.LATEST -> boardRepository.getLatestBoards(cursorId, FeedType.BOARD_PICTURE.name)
		else -> boardRepository.getPopularBoards(cursorId, FeedType.BOARD_PICTURE.name, sortType.name)
	}
}