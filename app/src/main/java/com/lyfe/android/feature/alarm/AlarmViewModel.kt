package com.lyfe.android.feature.alarm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetAlarmListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
	getAlarmListUseCase: GetAlarmListUseCase
) : ViewModel() {

	var uiState by mutableStateOf<AlarmUiState>(AlarmUiState.Loading)
		private set

	init {
		viewModelScope.launch {
			getAlarmListUseCase().collect {
				uiState = AlarmUiState.Success(it)
			}
		}
	}
}