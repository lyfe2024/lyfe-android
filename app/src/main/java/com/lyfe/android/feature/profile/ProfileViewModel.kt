package com.lyfe.android.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
	var uiState by mutableStateOf<ProfileUiState>(ProfileUiState.Loading)
		private set

	init {
		viewModelScope.launch {
			uiState = ProfileUiState.GuestSuccess
		}
	}

	fun updateUiState(updatedState: ProfileUiState) {
		uiState = updatedState
	}
}