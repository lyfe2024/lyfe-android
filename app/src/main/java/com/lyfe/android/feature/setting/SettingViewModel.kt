package com.lyfe.android.feature.setting

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.common.ui.permission.NeededPermission
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.DeleteAccountUseCase
import com.lyfe.android.core.domain.usecase.DeleteLocalDataUseCase
import com.lyfe.android.core.domain.usecase.GetSocialTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
	private val deleteAccountUseCase: DeleteAccountUseCase,
	private val getSocialTypeUseCase: GetSocialTypeUseCase,
	private val deleteLocalDataUseCase: DeleteLocalDataUseCase,
) : ViewModel() {

	var uiState by mutableStateOf<SettingUiState>(SettingUiState.IDLE)
		private set

	private val _event = MutableSharedFlow<SettingUiEvent>()
	val event = _event.asSharedFlow()

	val notificationPermission = getPermission()

	var socialType by mutableStateOf("")
		private set

	init {
		getSocialType()
	}

	private fun getSocialType() = viewModelScope.launch {
		socialType = getSocialTypeUseCase().first()
	}

	fun updateUiState(uiState: SettingUiState) {
		this.uiState = uiState
	}

	fun deleteAccount() {
		uiState = SettingUiState.Loading
		viewModelScope.launch {
			uiState = when (val response = deleteAccountUseCase()) {
				is Result.Failure -> {
					SettingUiState.Failure(message = response.error)
				}

				is Result.NetworkError -> {
					SettingUiState.Failure(message = response.exception.message)
				}

				is Result.Success -> {
					deleteLocalData()
					SettingUiState.DeleteAccountSuccess
				}

				is Result.Unexpected -> {
					SettingUiState.Failure(message = response.t.message)
				}
			}
		}
	}

	fun deleteLocalData() = viewModelScope.launch {
		deleteLocalDataUseCase()
	}

	private fun getPermission(): NeededPermission? {
		// SDK 13부터 이미지 및 사진에 대한 세부 권한 추가 요청 필요
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			NeededPermission.POST_NOTIFICATION
		} else {
			null
		}
	}

	fun checkPermission() = viewModelScope.launch {
		_event.emit(SettingUiEvent.CheckPermission)
	}

	fun denyPermission() = viewModelScope.launch {
		_event.emit(SettingUiEvent.ShowPermissionAlertDialog)
	}

	fun checkPermissionResult(
		passedPermission: NeededPermission?
	) = viewModelScope.launch {
		if (passedPermission != null && passedPermission.permission == notificationPermission?.permission) {
			_event.emit(SettingUiEvent.NotificationAllowed)
		} else {
			_event.emit(SettingUiEvent.ShowPermissionAlertDialog)
		}
	}
}