package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.TopicRepository
import javax.inject.Inject

class GetTodayTopicUseCase @Inject constructor(
	private val topicRepository: TopicRepository
) {
	// 오늘의 주제
	suspend operator fun invoke() = topicRepository.getTodayTopic()
}