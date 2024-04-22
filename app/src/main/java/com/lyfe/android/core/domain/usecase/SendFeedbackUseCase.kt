package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.FeedbackRepository
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(
	private val feedbackRepository: FeedbackRepository
) {

	suspend operator fun invoke(text: String) = feedbackRepository.sendFeedback(text)
}