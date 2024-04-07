package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.SendFeedbackRequest
import com.lyfe.android.core.data.model.SendFeedbackResult
import com.lyfe.android.core.data.network.model.Result

interface FeedbackDataSource {

	suspend fun sendFeedback(
		requestBody: SendFeedbackRequest
	): Result<SendFeedbackResult>
}