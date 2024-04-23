package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.Topic
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface TopicService {
	// 오늘의 주제 조회
	@GET("/v1/topics")
	suspend fun getTodayTopic(): Result<Topic>

	// 과거의 주제 조회
	@GET("/v1/topics/{date}")
	suspend fun getPastTopic(
		@Path("date") date: String
	): Result<Topic>
}