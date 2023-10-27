package com.lyfe.android.feature.alarm

import com.lyfe.android.core.model.Alarm

sealed interface AlarmUiState {

	object Loading : AlarmUiState

	data class Success(
		val alarmList: List<Alarm>
	) : AlarmUiState

	object Failure : AlarmUiState
}