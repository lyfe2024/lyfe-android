package com.lyfe.android.core.domain.usecase.board

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.BoardRepository
import com.lyfe.android.core.domain.repository.TopicRepository
import com.lyfe.android.core.domain.repository.UserRepository
import com.lyfe.android.core.domain.usecase.GetUploadLocalImageUrlUseCase
import com.lyfe.android.core.model.FeedType
import com.lyfe.android.core.model.board.RegisterBoardRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterLocalImageBoardUseCase @Inject constructor(
	private val uploadImageUrlUseCase: GetUploadLocalImageUrlUseCase,
	private val boardRepository: BoardRepository,
	private val topicRepository: TopicRepository,
	private val userRepository: UserRepository
) {
	operator fun invoke(
		title: String,
		localContentImageUrl: String
	) = flow {
		val uploadedImageUrl = uploadImageUrlUseCase(localContentImageUrl).firstOrNull() ?: return@flow
		val todayTopic = (topicRepository.getTodayTopic() as? Result.Success)?.body ?: return@flow

		userRepository.getUserInfo().flatMapLatest { userInfo ->
			boardRepository.registerBoard(
				registerBoardRequestData = RegisterBoardRequestData(
					title = title,
					content = uploadedImageUrl,
					boardType = FeedType.BOARD_PICTURE,
					userId = userInfo.id,
					topicId = todayTopic.id
				)
			)
		}.collect {
			emit(it)
		}
	}
}