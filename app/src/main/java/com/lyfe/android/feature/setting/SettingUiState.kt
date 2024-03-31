package com.lyfe.android.feature.setting

sealed interface SettingUiState {

	object IDLE : SettingUiState

	object DeleteAccountSuccess : SettingUiState

	object LogoutSuccess : SettingUiState

	object Loading : SettingUiState

	data class Failure(val message: String?) : SettingUiState
}