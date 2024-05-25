package com.lyfe.android.feature.profile.image

sealed interface ProfileImageFeedUiState {

	object Loading : ProfileImageFeedUiState
	object IDLE : ProfileImageFeedUiState
	data class Error(val message: String?) : ProfileImageFeedUiState
}