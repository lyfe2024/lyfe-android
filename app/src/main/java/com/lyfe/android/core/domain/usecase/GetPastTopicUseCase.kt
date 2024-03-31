package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TopicRepository
import javax.inject.Inject

class GetPastTopicUseCase @Inject constructor(
	private val topicRepository: TopicRepository
) {
	// 과거의 주제
	suspend operator fun invoke(date: String) = topicRepository.getPastTopic(date)
}