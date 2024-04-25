package com.lyfe.android.feature.feed

sealed interface PopularFeedListUiState {

	object Loading : PopularFeedListUiState
	object IDLE : PopularFeedListUiState
	data class Error(val message: String?) : PopularFeedListUiState
}