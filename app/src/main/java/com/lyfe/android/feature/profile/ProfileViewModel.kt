package com.lyfe.android.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetAccessTokenUseCase
import com.lyfe.android.core.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val getUserInfoUseCase: GetUserInfoUseCase,
	private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.IDLE)
	val uiState get() = _uiState.asStateFlow()

	init {
		getUserInfo()
	}

	private fun getUserInfo() = viewModelScope.launch {
		getAccessTokenUseCase().collect { token ->
			if (token.isNullOrEmpty()) {
				return@collect
			}
			getUserInfoUseCase().catch {
				_uiState.emit(ProfileUiState.Error(message = it.message))
			}.collect {
				_uiState.emit(ProfileUiState.UserLoaded(it))
			}
		}
	}
}