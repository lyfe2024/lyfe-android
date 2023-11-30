package com.lyfe.android.feature.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

	companion object {
		const val TAG = "LoginViewModel"
	}

	var uiState by mutableStateOf<LoginUiState>(LoginUiState.IDLE)
		private set

	fun updateUiState(state: LoginUiState) {
		Log.d(TAG, "UI State changed")
		uiState = state
	}
}