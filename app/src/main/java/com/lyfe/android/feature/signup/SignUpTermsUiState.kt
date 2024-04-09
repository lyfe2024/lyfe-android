package com.lyfe.android.feature.signup

sealed interface SignUpTermsUiState {

	object IDLE : SignUpTermsUiState

	object Loading : SignUpTermsUiState

	object Success : SignUpTermsUiState

	data class Failure(
		val errorMessage: String = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
	) : SignUpTermsUiState
}