package com.lyfe.android.feature.profile

sealed interface ProfileUiState {

	data class Success(
		val isGuest : Boolean
	) : ProfileUiState

	object Loading : ProfileUiState

	object Failure : ProfileUiState
}