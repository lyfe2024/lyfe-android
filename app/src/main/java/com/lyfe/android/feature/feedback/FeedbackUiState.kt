package com.lyfe.android.feature.feedback

sealed interface FeedbackUiState {

	object IDLE : FeedbackUiState

	object Loading : FeedbackUiState

	object Success : FeedbackUiState

	data class Failure(
		val errorMessage: String = "에러메세지가 존재하지 않습니다."
	) : FeedbackUiState
}