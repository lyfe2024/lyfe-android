package com.lyfe.android.feature.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SettingViewModel @Inject constructor() : ViewModel() {

	var uiState by mutableStateOf<SettingUiState>(SettingUiState.Success)
		private set
}