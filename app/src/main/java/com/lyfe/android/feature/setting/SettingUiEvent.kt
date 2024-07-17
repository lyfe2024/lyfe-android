package com.lyfe.android.feature.setting

sealed interface SettingUiEvent {

	object IDLE : SettingUiEvent

	object DeleteAccountSuccess : SettingUiEvent

	object LogoutSuccess : SettingUiEvent

	object Loading : SettingUiEvent

	data class Failure(val message: String?) : SettingUiEvent

	object CheckPermission : SettingUiEvent

	object NotificationAllowed : SettingUiEvent

	object ShowPermissionAlertDialog : SettingUiEvent
}