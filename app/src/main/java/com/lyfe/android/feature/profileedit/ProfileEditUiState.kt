package com.lyfe.android.feature.profileedit

sealed interface ProfileEditUiState {

	object IDLE : ProfileEditUiState
	object Loading : ProfileEditUiState

	data class Success(
		val thumbnail: String = "",
		val nickname: String
	) : ProfileEditUiState

	data class Failure(
		val message: String
	) : ProfileEditUiState
}