package com.lyfe.android.feature.post.create.text

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.board.RegisterTextBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTextPostViewModel @Inject constructor(
	private val registerTextBoardUseCase: RegisterTextBoardUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow(CreateTextPostUiState())
	val uiState get() = _uiState.asStateFlow()

	fun setTitle(title: String) {
		_uiState.update {
			uiState.value.copy(
				title = title
			)
		}
	}

	fun setContent(content: String) {
		_uiState.update {
			uiState.value.copy(
				content = content
			)
		}
	}

	fun createTextPost() {
		if (!uiState.value.isPostValidation()) return
		
		viewModelScope.launch {
			registerTextBoardUseCase.invoke(
				title = uiState.value.title,
				content = uiState.value.content
			).collectLatest { result ->
				when (result) {
					is Result.Success -> {
						_uiState.update {
							uiState.value.copy(event = CreateTextPostUiEvent.CreateSuccess(result.body.id))
						}
					}
					is Result.Failure -> {
						_uiState.update {
							uiState.value.copy(event = CreateTextPostUiEvent.CreateFail(result.error))
						}
					}
					is Result.Unexpected -> {
						_uiState.update {
							uiState.value.copy(event = CreateTextPostUiEvent.CreateFail(result.t.message))
						}
					}
					is Result.NetworkError -> {
						_uiState.update {
							uiState.value.copy(event = CreateTextPostUiEvent.CreateFail(result.exception.message))
						}
					}
				}
			}
		}
	}

	fun clearEvent() {
		_uiState.update {
			uiState.value.copy(event = CreateTextPostUiEvent.IDLE)
		}
	}
}