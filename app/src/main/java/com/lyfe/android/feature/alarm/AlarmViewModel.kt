package com.lyfe.android.feature.alarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetNotificationsUseCase
import com.lyfe.android.core.model.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
	private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<AlarmUiState>(AlarmUiState.Loading)
	val uiState = _uiState.asStateFlow()

	private val prevNotificationList = mutableListOf<Notification>()
	private val notificationFetchingLastNotiId = MutableStateFlow(0L)
	val notificationList: StateFlow<List<Notification>> = notificationFetchingLastNotiId.flatMapLatest { lastNotiId ->
		getNotificationsUseCase(
			lastNotiId = lastNotiId,
			onStart = { _uiState.value = AlarmUiState.Loading },
			onCompletion = { _uiState.value = AlarmUiState.IDLE },
			onError = { _uiState.value = AlarmUiState.Error(it) }
		).map {
			Log.e("Test@@@", "it: ${it.notificationList}")
			prevNotificationList.addAll(it.notificationList)
			prevNotificationList.toList()
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = emptyList()
	)

	fun fetchNextNotifications() {
		Log.e("Test@@@", "fetchNextNotifications")
		if (uiState.value != AlarmUiState.Loading) {
//			notificationFetchingLastNotiId.value = prevNotificationList.last().id
			notificationFetchingLastNotiId.value++
		}
	}
}