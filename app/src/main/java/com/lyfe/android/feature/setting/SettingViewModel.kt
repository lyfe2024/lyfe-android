package com.lyfe.android.feature.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.DeleteAccountUseCase
import com.lyfe.android.core.domain.usecase.GetSocialTypeUseCase
import com.lyfe.android.feature.login.GoogleLoginManager
import com.lyfe.android.feature.login.KakaoLoginManager
import com.lyfe.android.feature.login.SocialType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(
	private val deleteAccountUseCase: DeleteAccountUseCase,
	private val getSocialTypeUseCase: GetSocialTypeUseCase
) : ViewModel() {

	var uiState by mutableStateOf<SettingUiState>(SettingUiState.IDLE)
		private set

	suspend fun getSocialType(): String {
		return getSocialTypeUseCase().first()
	}

	fun updateUiState(uiState: SettingUiState) {
		this.uiState = uiState
	}

	fun deleteAccount() {
		uiState = SettingUiState.Loading
		viewModelScope.launch {
			uiState = when (val response = deleteAccountUseCase()) {
				is Result.Failure -> {
					SettingUiState.Failure(message = response.error)
				}

				is Result.NetworkError -> {
					SettingUiState.Failure(message = response.exception.message)
				}

				is Result.Success -> {
					SettingUiState.DeleteAccountSuccess
				}

				is Result.Unexpected -> {
					SettingUiState.Failure(message = response.t?.message)
				}
			}
		}
	}
}