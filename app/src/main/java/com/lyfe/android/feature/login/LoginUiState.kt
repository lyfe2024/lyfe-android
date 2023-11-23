package com.lyfe.android.feature.login

sealed interface LoginUiState {

	object IDLE : LoginUiState

	object Loading : LoginUiState

	object Success : LoginUiState

	data class Failure(
		val reason: String?
	) : LoginUiState
}