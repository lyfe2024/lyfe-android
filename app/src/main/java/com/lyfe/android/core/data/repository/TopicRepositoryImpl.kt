package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.TopicDataSource
import com.lyfe.android.core.data.model.Topic
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.TopicRepository
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
	private val topicDataSource: TopicDataSource
) : TopicRepository {
	override suspend fun getTodayTopic(): Result<Topic> {
		return topicDataSource.getTodayTopic()
	}
}