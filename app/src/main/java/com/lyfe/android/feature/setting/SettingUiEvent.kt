package com.lyfe.android.feature.setting

sealed interface SettingUiEvent {

	object IDLE : SettingUiEvent

	object CheckPermission : SettingUiEvent

	object NotificationAllowed : SettingUiEvent

	object ShowPermissionAlertDialog : SettingUiEvent
}