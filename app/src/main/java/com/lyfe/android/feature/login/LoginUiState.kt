package com.lyfe.android.feature.login

sealed interface LoginUiState {

	object IDLE : LoginUiState

	object Loading : LoginUiState

	object Success : LoginUiState

	object SignedIn : LoginUiState

	data class Failure(
		val errorMessage: String = "에러메세지가 존재하지 않습니다."
	) : LoginUiState
}