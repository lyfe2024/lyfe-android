package com.lyfe.android.feature.profile

import com.lyfe.android.core.model.User

sealed interface ProfileUiState {

	object IDLE : ProfileUiState

	object Guest : ProfileUiState

	data class UserLoaded(
		val user: User
	) : ProfileUiState

	data class Error(
		val code: Int? = null,
		val message: String? = null
	) : ProfileUiState
}