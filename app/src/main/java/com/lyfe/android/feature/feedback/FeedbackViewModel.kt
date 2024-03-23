package com.lyfe.android.feature.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.SendFeedbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
	private val sendFeedbackUseCase: SendFeedbackUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<FeedbackUiState>(FeedbackUiState.IDLE)
	val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()

	fun sendFeedback(text: String) = viewModelScope.launch {
		if (_uiState.value == FeedbackUiState.Loading) {
			return@launch
		}
		_uiState.value = FeedbackUiState.Loading
		_uiState.value = when (val response = sendFeedbackUseCase(text)) {
			is Result.Success -> {
				FeedbackUiState.Success
			}

			is Result.Failure -> {
				FeedbackUiState.Failure(response.error ?: "서버와의 연결 도중 오류가 발생하였습니다.")
			}

			is Result.NetworkError -> {
				FeedbackUiState.Failure("네트워크 연결 상태를 확인해주세요.")
			}

			is Result.Unexpected -> {
				FeedbackUiState.Failure("오류가 발생하였습니다. 잠시 후 다시 시도해주세요.")
			}
		}
	}
}