package com.lyfe.android.feature.post.create.text

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateTextPostViewModel @Inject constructor(
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

	}
}