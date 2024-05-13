package com.lyfe.android.feature.feed.text

sealed interface TextFeedListUiState {

	object Loading : TextFeedListUiState
	object IDLE : TextFeedListUiState
	data class Error(val message: String?) : TextFeedListUiState
}