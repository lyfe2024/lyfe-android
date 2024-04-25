package com.lyfe.android.feature.feed

sealed interface LatestFeedListUiState {

	object Loading : LatestFeedListUiState
	object IDLE : LatestFeedListUiState
	data class Error(val message: String?) : LatestFeedListUiState
}