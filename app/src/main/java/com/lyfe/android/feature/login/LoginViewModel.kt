package com.lyfe.android.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.AuthUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val authUserUseCase: AuthUserUseCase
) : ViewModel() {

	var uiState by mutableStateOf<LoginUiState>(LoginUiState.IDLE)
		private set

	fun updateUiState(state: LoginUiState) {
		uiState = state
	}

	fun authUser(
		socialType: String,
		authorizationCode: String,
		identityToken: String,
		fcmToken: String
	) {
		viewModelScope.launch {
			authUserUseCase(socialType, authorizationCode, identityToken, fcmToken).collect {
				when(it) {
					is Result.Success -> {
						// TODO 소셜 로그인 성공 (유저 토큰)
					}
					is Result.Failure -> {
						// TODO 소셜 로그인 실패
					}
					is Result.NetworkError -> {
						// TODO 네트워크 에러
					}
					is Result.Unexpected -> {
						// TODO 예상치 못한 오류
					}
				}
			}
		}
	}
}