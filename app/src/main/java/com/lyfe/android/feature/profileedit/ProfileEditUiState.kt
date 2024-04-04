package com.lyfe.android.feature.profileedit

sealed interface ProfileEditUiState {

	object IDLE : ProfileEditUiState
	object Loading : ProfileEditUiState

	object Success : ProfileEditUiState

	data class Failure(
		val message: String
	) : ProfileEditUiState
}