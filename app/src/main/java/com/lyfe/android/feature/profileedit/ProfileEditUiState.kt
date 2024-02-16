package com.lyfe.android.feature.profileedit

import com.lyfe.android.core.model.User

sealed interface ProfileEditUiState {

	object IDLE : ProfileEditUiState
	object Loading : ProfileEditUiState

	data class Success(
		val user: User
	) : ProfileEditUiState

	data class Failure(
		val message: String
	) : ProfileEditUiState
}