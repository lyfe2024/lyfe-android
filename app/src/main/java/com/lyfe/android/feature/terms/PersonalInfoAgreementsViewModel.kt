package com.lyfe.android.feature.terms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.GetPersonalInfoAgreementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoAgreementsViewModel @Inject constructor(
	private val getPersonalInfoAgreementsUseCase: GetPersonalInfoAgreementsUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<TermsUiState>(TermsUiState.Loading)
	val uiState: StateFlow<TermsUiState> = _uiState.asStateFlow()

	init {
		getPersonalInfoAgreements()
	}

	private fun getPersonalInfoAgreements() = viewModelScope.launch {
		when (val response = getPersonalInfoAgreementsUseCase()) {
			is Result.Success -> {
				val terms = response.body?.result
				_uiState.value = if (terms == null) {
					TermsUiState.Failure()
				} else {
					TermsUiState.Success(terms.content)
				}
			}
			is Result.Failure -> {
				_uiState.value = TermsUiState.Failure(response.error ?: "")
			}
			is Result.NetworkError -> {
				_uiState.value = TermsUiState.Failure("네트워크 에러")
			}
			is Result.Unexpected -> {
				_uiState.value = TermsUiState.Failure("예기치 못한 에러")
			}
		}
	}
}