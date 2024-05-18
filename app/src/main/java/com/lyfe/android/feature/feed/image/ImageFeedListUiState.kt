package com.lyfe.android.feature.feed.image

sealed interface ImageFeedListUiState {

	object Loading : ImageFeedListUiState
	object IDLE : ImageFeedListUiState
	data class Error(val message: String?) : ImageFeedListUiState
}