package com.lyfe.android.feature.nickname

sealed interface CreateNicknameUiState {

	object IDLE : CreateNicknameUiState
	object Loading : CreateNicknameUiState
	object Duplicated : CreateNicknameUiState
	object Success : CreateNicknameUiState
}