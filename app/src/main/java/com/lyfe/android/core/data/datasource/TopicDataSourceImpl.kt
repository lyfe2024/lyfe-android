package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetPastTopicResponse
import com.lyfe.android.core.data.model.GetTodayTopicResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.TopicService
import javax.inject.Inject

class TopicDataSourceImpl @Inject constructor(
	private val topicService: TopicService
) : TopicDataSource {

	override suspend fun getTodayTopic(): Result<GetTodayTopicResponse> {
		return topicService.getTodayTopic()
	}

	override suspend fun getPastTopic(date: String): Result<GetPastTopicResponse> {
		return topicService.getPastTopic(date)
	}
}