package com.lyfe.android.feature.terms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.GetServiceTermsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceTermsViewModel @Inject constructor(
	private val getServiceTermsUseCase: GetServiceTermsUseCase
) : ViewModel() {

	var uiState by mutableStateOf<TermsUiState>(TermsUiState.Loading)
		private set

	init {
		getServiceTerms()
	}

	private fun getServiceTerms() = viewModelScope.launch {
		when (val response = getServiceTermsUseCase()) {
			is Result.Success -> {
				val terms = response.body?.result
				uiState = if (terms == null) {
					TermsUiState.Failure()
				} else {
					TermsUiState.Success(terms.content)
				}
			}
			is Result.Failure -> {
				uiState = TermsUiState.Failure(response.error ?: "")
			}
			is Result.NetworkError -> {
				uiState = TermsUiState.Failure("네트워크 에러")
			}
			is Result.Unexpected -> {
				uiState = TermsUiState.Failure("예기치 못한 에러")
			}
		}
	}
}