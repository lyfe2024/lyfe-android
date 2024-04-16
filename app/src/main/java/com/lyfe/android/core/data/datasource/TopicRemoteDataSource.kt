package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.Topic
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.TopicService
import javax.inject.Inject

class TopicRemoteDataSource @Inject constructor(
	private val topicService: TopicService
) : TopicDataSource {

	override suspend fun getTodayTopic(): Result<Topic> {
		return topicService.getTodayTopic()
	}
}