package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.TopicDataSource
import com.lyfe.android.core.data.model.Topic
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.TopicRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
	private val topicDataSource: TopicDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : TopicRepository {
	override suspend fun getTodayTopic(): Result<Topic> {
		return withContext(ioDispatcher) {
			topicDataSource.getTodayTopic()
		}
	}
}