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
import com.lyfe.android.core.domain.usecase.GetAccessTokenUseCase
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
	private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

	private val _event = MutableSharedFlow<SettingUiEvent>()
	val event = _event.asSharedFlow()

	var isGuest by mutableStateOf(true)
		private set

	var socialType by mutableStateOf("")
		private set

	var menuList by mutableStateOf<List<Setting>>(listOf())
		private set

	val notificationPermission = getPermission()


	init {
		getSocialType()
		checkGuest()
	}

	private fun getSocialType() = viewModelScope.launch {
		socialType = getSocialTypeUseCase().first()
	}

	private fun checkGuest() = viewModelScope.launch {
		getAccessTokenUseCase().collect { token ->
			isGuest = token.isNullOrEmpty()
			setMenuList(isGuest)
		}
	}

	private fun setMenuList(isGuest: Boolean) {
		menuList = if (isGuest) {
			listOf(
				Setting.TERMS,
				Setting.PRIVACY_POLICY
			)
		} else {
			listOf(
				Setting.NOTIFICATION,
				Setting.USER_EXPERIENCE,
				Setting.TERMS,
				Setting.PRIVACY_POLICY,
				Setting.DELETE_ACCOUNT
			)
		}
	}

	fun emitEvent(event: SettingUiEvent) = viewModelScope.launch {
		_event.emit(event)
	}

	fun deleteAccount() = viewModelScope.launch {
		_event.emit(SettingUiEvent.Loading)
		when (val response = deleteAccountUseCase()) {
			is Result.Failure -> {
				_event.emit(SettingUiEvent.Failure(message = response.error))
			}

			is Result.NetworkError -> {
				_event.emit(SettingUiEvent.Failure(message = response.exception.message))
			}

			is Result.Success -> {
				deleteLocalData()
				_event.emit(SettingUiEvent.DeleteAccountSuccess)
			}

			is Result.Unexpected -> {
				_event.emit(SettingUiEvent.Failure(message = response.t.message))
			}
		}
	}

	fun logout() = viewModelScope.launch {
		deleteLocalData()
		_event.emit(SettingUiEvent.LogoutSuccess)
	}

	private suspend fun deleteLocalData() {
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