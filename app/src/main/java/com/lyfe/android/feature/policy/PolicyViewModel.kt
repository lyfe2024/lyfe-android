package com.lyfe.android.feature.policy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.DeleteSignUpTokenUseCase
import com.lyfe.android.core.domain.usecase.GetPersonalInfoAgreementsUseCase
import com.lyfe.android.core.domain.usecase.GetServiceTermsUseCase
import com.lyfe.android.core.domain.usecase.GetSignUpTokenUseCase
import com.lyfe.android.core.domain.usecase.SignUpUserUseCase
import com.lyfe.android.core.domain.usecase.UpdateAccessTokenUseCase
import com.lyfe.android.core.domain.usecase.UpdateRefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyViewModel @Inject constructor(
	private val signUpUserUseCase: SignUpUserUseCase,
	private val deleteSignUpTokenUseCase: DeleteSignUpTokenUseCase,
	private val updateAccessTokenUseCase: UpdateAccessTokenUseCase,
	private val updateRefreshTokenUseCase: UpdateRefreshTokenUseCase,
	private val getSignUpTokenUseCase: GetSignUpTokenUseCase
) : ViewModel() {

	var uiState by mutableStateOf<PolicyUiState>(PolicyUiState.IDLE)
		private set

	fun postUser(
		nickname: String
	) = viewModelScope.launch {
		uiState = PolicyUiState.Loading
		val userToken = getSignUpTokenUseCase().first()
		when (val response = signUpUserUseCase(userToken, nickname)) {
			is Result.Success -> {
				val result = response.body?.result
				uiState = if (result == null) {
					PolicyUiState.Failure()
				} else {
					// 회원가입 토큰 삭제
					deleteSignUpTokenUseCase()
					// 토큰 저장
					updateAccessTokenUseCase(result.accessToken)
					updateRefreshTokenUseCase(result.refreshToken)
					PolicyUiState.Success
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