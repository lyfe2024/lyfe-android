package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SendFeedbackResponse(
	val result: SendFeedbackResult
)

@Serializable
data class SendFeedbackResult(
	val id: Int
)