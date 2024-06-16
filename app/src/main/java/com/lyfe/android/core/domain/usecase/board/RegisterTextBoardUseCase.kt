package com.lyfe.android.core.domain.usecase.board

import android.util.Log
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.BoardRepository
import com.lyfe.android.core.domain.repository.TopicRepository
import com.lyfe.android.core.domain.repository.UserRepository
import com.lyfe.android.core.model.FeedType
import com.lyfe.android.core.model.board.RegisterBoardRequestData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterTextBoardUseCase @Inject constructor(
	private val boardRepository: BoardRepository,
	private val userRepository: UserRepository,
	private val topicRepository: TopicRepository
) {
	operator fun invoke(
		title: String,
		content: String
	) = flow {
		val userInfo = userRepository.getUserInfo().firstOrNull() ?: return@flow
		val topic = (topicRepository.getTodayTopic() as? Result.Success)?.body ?: return@flow

		boardRepository.registerBoard(
			registerBoardRequestData = RegisterBoardRequestData(
				title = title,
				content = content,
				boardType = FeedType.BOARD,
				userId = userInfo.id,
				topicId = topic.id
			)
		).collect {
			emit(it)
		}
	}
}