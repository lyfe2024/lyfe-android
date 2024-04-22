package com.lyfe.android.feature.terms

sealed interface TermsUiState {

	object Loading : TermsUiState

	data class Success(val content: String) : TermsUiState

	data class Failure(
		val errorMessage: String = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
	) : TermsUiState
}