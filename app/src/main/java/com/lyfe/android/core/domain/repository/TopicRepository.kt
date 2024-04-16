package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.Topic
import com.lyfe.android.core.data.network.model.Result

interface TopicRepository {

	suspend fun getTodayTopic(): Result<Topic>
}