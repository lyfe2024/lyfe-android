package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.model.SendFeedbackResponse
import com.lyfe.android.core.data.network.model.Result

interface FeedbackRepository {
	suspend fun sendFeedback(text: String): Result<SendFeedbackResponse>
}