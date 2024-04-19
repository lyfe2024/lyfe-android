package com.lyfe.android.feature.alarm

import com.lyfe.android.core.model.Notification

sealed interface AlarmUiState {

	object Loading : AlarmUiState
	object IDLE : AlarmUiState
	data class Error(val message: String?) : AlarmUiState

}