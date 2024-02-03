package com.lyfe.android.feature.policy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.model.PostUserResult
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.GetAccessTokenUseCase
import com.lyfe.android.core.domain.usecase.SaveAccessTokenUseCase
import com.lyfe.android.core.domain.usecase.SaveRefreshTokenUseCase
import com.lyfe.android.core.domain.usecase.SignUpUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyViewModel @Inject constructor(
	private val signUpUserUseCase: SignUpUserUseCase,
	private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
	private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
	private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

	var uiState by mutableStateOf<PolicyUiState>(PolicyUiState.IDLE)
		private set

	fun postUser(
		nickname: String
	) = viewModelScope.launch {
		uiState = PolicyUiState.Loading
		getAccessTokenUseCase().collect { userToken ->
			when(val response = signUpUserUseCase(userToken, nickname)) {
				is Result.Success -> {
					val result = response.body?.result
					if (result == null) {
						uiState = PolicyUiState.Failure()
					} else {
						saveUserToken(result) // 토큰 저장
						uiState = PolicyUiState.Success
					}
				}
				is Result.Failure -> {
					// TODO
				}
				is Result.NetworkError -> {
					// TODO
				}
				is Result.Unexpected -> {
					// TODO
				}
			}
		}
	}

	private suspend fun saveUserToken(
		result: PostUserResult
	) {
		// 토큰 저장
		saveAccessTokenUseCase(result.accessToken)
		saveRefreshTokenUseCase(result.refreshToken)
	}
}