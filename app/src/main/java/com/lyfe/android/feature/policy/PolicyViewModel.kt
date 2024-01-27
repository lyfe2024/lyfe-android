package com.lyfe.android.feature.policy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.model.PostUserResult
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.SignUpUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyViewModel @Inject constructor(
	private val signUpUserUseCase: SignUpUserUseCase
) : ViewModel() {

	var uiState by mutableStateOf<PolicyUiState>(PolicyUiState.IDLE)
		private set

	fun updateUiState(state: PolicyUiState) {
		uiState = state
	}

	fun postUser(
		userToken: String,
		nickname: String
	) = viewModelScope.launch {
		uiState = PolicyUiState.Loading
		val response = signUpUserUseCase(userToken, nickname)
		when(response) {
			is Result.Success -> {
				val result = response.body?.result
				if (result == null) {
					uiState = PolicyUiState.Failure()
				} else {
					saveUserTokenInfo(result) // 토큰 저장
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

	private fun saveUserTokenInfo(
		result: PostUserResult
	) {
		// TODO 토큰 저장 (DataStore?)
	}
}