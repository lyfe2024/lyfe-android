package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetPastTopicResponse
import com.lyfe.android.core.data.model.GetTodayTopicResponse
import com.lyfe.android.core.data.network.model.Result

interface TopicDataSource {

	suspend fun getTodayTopic(): Result<GetTodayTopicResponse>

	suspend fun getPastTopic(date: String): Result<GetPastTopicResponse>
}