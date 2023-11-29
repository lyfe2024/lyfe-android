package com.lyfe.android.feature.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.BuildConfig
import com.lyfe.android.core.data.model.GoogleTokenRequest
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.GoogleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val googleRepository: GoogleRepository
) : ViewModel() {

	companion object {
		const val TAG = "LoginViewModel"
	}

	var uiState by mutableStateOf<LoginUiState>(LoginUiState.IDLE)
		private set

	fun updateUiState(state: LoginUiState) {
		uiState = state
	}

	fun getGoogleAccessToken(authCode: String) {
		viewModelScope.launch {
			googleRepository.getAccessToken(
				GoogleTokenRequest(
					grant_type = "authorization_code",
					client_id = BuildConfig.GOOGLE_WEB_CLIENT_ID,
					client_secret = BuildConfig.GOOGLE_WEB_CLIENT_SECRET,
					code = authCode
				)
			).collect { result ->
				when (result) {
					is Result.Success -> {
						val accessToken = result.body?.access_token
						Log.d(TAG, accessToken ?: "token is empty")
						uiState = LoginUiState.Success
					}
					is Result.Failure -> {
						val errorMessage = result.error ?: "error message is empty"
						Log.e(TAG, "구글 액세스 토큰 발급 실패")
						Log.e(TAG, errorMessage)
						uiState = LoginUiState.Failure(errorMessage)
					}
					is Result.NetworkError -> {
						Log.e(TAG, "네트워크 에러 발생")
						uiState = LoginUiState.Failure("네트워크 에러 발생")
						throw result.exception
					}
					is Result.Unexpected -> {
						Log.e(TAG, "예기치 못한 오류 발생")
						uiState = LoginUiState.Failure("예기치 못한 오류 발생")
						throw result.t ?: return@collect
					}
				}
			}
		}
	}
}