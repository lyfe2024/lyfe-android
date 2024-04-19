package com.lyfe.android.feature.alarm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.GetNotificationsUseCase
import com.lyfe.android.core.model.Notifications
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
	getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

	var uiState by mutableStateOf<AlarmUiState>(AlarmUiState.Loading)
		private set

	init {
		viewModelScope.launch {
			getNotificationsUseCase().collectLatest {
				if (it is Result.Success) {
					Log.e("Test@@@", "AlarmViewModel ${it.body}")
					uiState = AlarmUiState.Success((it.body as? Notifications)?.notificationList ?: emptyList())
				}
			}
		}
	}
}