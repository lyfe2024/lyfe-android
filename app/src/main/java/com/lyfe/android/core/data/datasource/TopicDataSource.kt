package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.Topic
import com.lyfe.android.core.data.network.model.Result

interface TopicDataSource {

	suspend fun getTodayTopic(): Result<Topic>
}