package com.lyfe.android.feature.alarm

sealed interface NotificationListUiState {

	object Loading : NotificationListUiState
	object IDLE : NotificationListUiState
	data class Error(val message: String?) : NotificationListUiState
}