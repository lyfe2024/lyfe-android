package com.lyfe.android.feature.home

sealed interface HomeUiState {

	object Loading : HomeUiState

	object TodayTopicSuccess : HomeUiState

	object PastBestSuccess : HomeUiState

	data class Failure(
		val errorMessage: String = "에러메세지가 존재하지 않습니다."
	) : HomeUiState
}