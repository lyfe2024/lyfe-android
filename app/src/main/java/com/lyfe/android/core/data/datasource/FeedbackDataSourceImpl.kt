package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.SendFeedbackRequest
import com.lyfe.android.core.data.model.SendFeedbackResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.FeedbackService
import javax.inject.Inject

class FeedbackDataSourceImpl @Inject constructor(
	private val feedbackService: FeedbackService
) : FeedbackDataSource {

	override suspend fun sendFeedback(requestBody: SendFeedbackRequest): Result<SendFeedbackResponse> {
		return feedbackService.sendFeedback(requestBody)
	}
}