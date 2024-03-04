package com.lyfe.android.feature.policy

sealed interface PolicyUiState {

	object IDLE : PolicyUiState

	object Loading : PolicyUiState

	object Success : PolicyUiState

	data class Failure(
		val errorMessage: String = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
	) : PolicyUiState
}