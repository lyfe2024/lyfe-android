package com.lyfe.android.core.data.network.service

import com.lyfe.android.core.data.model.SendFeedbackRequest
import com.lyfe.android.core.data.model.SendFeedbackResponse
import com.lyfe.android.core.data.network.model.Result
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackService {

	// 피드백 보내기 API
	@POST("/v1/feedbacks")
	suspend fun sendFeedback(@Body body: SendFeedbackRequest): Result<SendFeedbackResponse>
}