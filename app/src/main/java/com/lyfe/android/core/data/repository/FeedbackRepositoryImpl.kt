package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.FeedbackDataSource
import com.lyfe.android.core.data.model.SendFeedbackRequest
import com.lyfe.android.core.data.model.SendFeedbackResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.FeedbackRepository
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
	private val feedbackDataSource: FeedbackDataSource
) : FeedbackRepository {

	override suspend fun sendFeedback(text: String): Result<SendFeedbackResponse> {
		val requestBody = SendFeedbackRequest(text)
		return feedbackDataSource.sendFeedback(requestBody)
	}
}