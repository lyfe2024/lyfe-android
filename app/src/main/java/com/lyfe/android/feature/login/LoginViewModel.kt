package com.lyfe.android.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.lyfe.android.core.domain.repository.LocalPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val localPreferenceRepository: LocalPreferenceRepository
) : ViewModel() {

	var uiState by mutableStateOf<LoginUiState>(LoginUiState.IDLE)
		private set

	fun updateUiState(state: LoginUiState) {
		uiState = state
	}

	fun updateKakaoToken(token: OAuthToken) {
		localPreferenceRepository.updateAccessToken(token.accessToken)
		uiState = LoginUiState.Success
	}
}