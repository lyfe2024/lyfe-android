package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.TopicDataSource
import com.lyfe.android.core.data.model.GetPastTopicResponse
import com.lyfe.android.core.data.model.GetTodayTopicResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.TopicRepository
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
	private val topicDataSource: TopicDataSource
) : TopicRepository {
	override suspend fun getTodayTopic(): Result<GetTodayTopicResponse> {
		return topicDataSource.getTodayTopic()
	}

	override suspend fun getPastTopic(date: String): Result<GetPastTopicResponse> {
		return topicDataSource.getPastTopic(date)
	}
}