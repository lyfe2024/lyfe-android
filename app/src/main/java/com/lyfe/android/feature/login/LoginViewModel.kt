package com.lyfe.android.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.AuthUserUseCase
import com.lyfe.android.core.domain.usecase.SaveAccessTokenUseCase
import com.lyfe.android.core.domain.usecase.SaveRefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val authUserUseCase: AuthUserUseCase,
	private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
	private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase
) : ViewModel() {

	var uiState by mutableStateOf<LoginUiState>(LoginUiState.IDLE)
		private set

	fun updateUiState(state: LoginUiState) {
		uiState = state
	}

	fun authUser(
		socialType: String,
		authorizationCode: String = "",
		identityToken: String = "",
		fcmToken: String = ""
	) {
		uiState = LoginUiState.Loading
		viewModelScope.launch {
			when(val response = authUserUseCase(socialType, authorizationCode, identityToken, fcmToken)) {
				is Result.Success -> {
					// 소셜 로그인 성공 (유저 토큰)
					LogUtil.d("authUser", response.body.toString())
					saveAccessTokenUseCase(response.body!!.result.userToken)
					uiState = LoginUiState.Success
				}
				is Result.Failure -> {
					// 소셜 로그인 실패
					LogUtil.e("authUser", "Failure message: ${response.error ?: ""}")
					uiState = LoginUiState.Failure()
				}
				is Result.NetworkError -> {
					// 네트워크 에러
					LogUtil.e("authUser", "Network Error message: ${response.exception.message ?: ""}")
					uiState = LoginUiState.Failure()
				}
				is Result.Unexpected -> {
					// 예상치 못한 오류
					LogUtil.e("authUser", "Unexpected message: ${response.t?.message ?: ""}")
					uiState = LoginUiState.Failure()
				}
			}
		}
	}
}