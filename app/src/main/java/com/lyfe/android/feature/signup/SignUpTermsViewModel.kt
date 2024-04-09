package com.lyfe.android.feature.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.DeleteSignUpTokenUseCase
import com.lyfe.android.core.domain.usecase.GetSignUpTokenUseCase
import com.lyfe.android.core.domain.usecase.SignUpUserUseCase
import com.lyfe.android.core.domain.usecase.UpdateAccessTokenUseCase
import com.lyfe.android.core.domain.usecase.UpdateRefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpTermsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val signUpUserUseCase: SignUpUserUseCase,
	private val deleteSignUpTokenUseCase: DeleteSignUpTokenUseCase,
	private val updateAccessTokenUseCase: UpdateAccessTokenUseCase,
	private val updateRefreshTokenUseCase: UpdateRefreshTokenUseCase,
	private val getSignUpTokenUseCase: GetSignUpTokenUseCase
) : ViewModel() {

	private val nicknameFlow = savedStateHandle.getStateFlow("nickname", "")

	private val _checkBoxUiState = MutableStateFlow(SignUpTermsCheckBoxUiState.Checked())
	val checkBoxUiState = _checkBoxUiState.asStateFlow()

	private val _signUpTermsUiState = MutableStateFlow<SignUpTermsUiState>(SignUpTermsUiState.IDLE)
	val signUpTermsUiState = _signUpTermsUiState.asStateFlow()

	fun postUser() = viewModelScope.launch {
		_signUpTermsUiState.value = SignUpTermsUiState.Loading

		getSignUpTokenUseCase().zip(nicknameFlow) { token, nickname ->
			signUpUserUseCase(token, nickname)
		}.collectLatest { response ->
			when (response) {
				is Result.Success -> {
					val result = response.body
					_signUpTermsUiState.value = if (result == null) {
						SignUpTermsUiState.Failure()
					} else {
						// 회원가입 토큰 삭제
						deleteSignUpTokenUseCase()
						// 토큰 저장
						updateAccessTokenUseCase(result.accessToken)
						updateRefreshTokenUseCase(result.refreshToken)
						SignUpTermsUiState.Success
					}
				}

				is Result.Failure -> {
					_signUpTermsUiState.value = SignUpTermsUiState.Failure()
				}

				is Result.NetworkError -> {
					_signUpTermsUiState.value = SignUpTermsUiState.Failure()
				}

				is Result.Unexpected -> {
					_signUpTermsUiState.value = SignUpTermsUiState.Failure()
				}
			}
		}
	}

	fun toggleAllPolicyAgree() {
		_checkBoxUiState.value = checkBoxUiState.value.copy(
			servicePolicy = !checkBoxUiState.value.servicePolicy,
			userInfoPolicy = !checkBoxUiState.value.userInfoPolicy
		)
	}

	fun toggleServicePolicy() {
		_checkBoxUiState.value = checkBoxUiState.value.copy(
			servicePolicy = !checkBoxUiState.value.servicePolicy
		)
	}

	fun toggleUserInfoPolicy() {
		_checkBoxUiState.value = checkBoxUiState.value.copy(
			userInfoPolicy = !checkBoxUiState.value.userInfoPolicy
		)
	}

	fun clear() {
		_checkBoxUiState.value = SignUpTermsCheckBoxUiState.Checked()
		_signUpTermsUiState.value = SignUpTermsUiState.IDLE
	}
}