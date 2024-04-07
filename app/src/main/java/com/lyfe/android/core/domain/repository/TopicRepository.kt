package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.GetPastTopicResponse
import com.lyfe.android.core.data.model.GetTodayTopicResponse
import com.lyfe.android.core.data.network.model.Result

interface TopicRepository {

	suspend fun getTodayTopic(): Result<GetTodayTopicResponse>

	suspend fun getPastTopic(
		date: String
	): Result<GetPastTopicResponse>
}