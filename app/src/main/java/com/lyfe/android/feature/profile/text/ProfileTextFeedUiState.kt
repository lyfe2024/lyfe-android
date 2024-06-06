package com.lyfe.android.feature.profile.text

sealed interface ProfileTextFeedUiState {

	object Loading : ProfileTextFeedUiState
	object IDLE : ProfileTextFeedUiState
	data class Error(val message: String?) : ProfileTextFeedUiState
}