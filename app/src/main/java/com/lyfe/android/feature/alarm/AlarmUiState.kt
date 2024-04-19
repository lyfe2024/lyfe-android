package com.lyfe.android.feature.alarm

import com.lyfe.android.core.model.Notification

sealed interface AlarmUiState {

	object Loading : AlarmUiState

	data class Success(
		val notificationList: List<Notification>
	) : AlarmUiState

	object Failure : AlarmUiState
}