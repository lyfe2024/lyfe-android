package com.lyfe.android.feature.setting

sealed interface SettingUiState{

    object Success : SettingUiState

    object Loading : SettingUiState

    object Failure : SettingUiState

}