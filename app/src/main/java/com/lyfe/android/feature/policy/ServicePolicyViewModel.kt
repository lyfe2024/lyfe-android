package com.lyfe.android.feature.policy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.PolicyRepository
import com.lyfe.android.core.model.ServicePolicyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ServicePolicyViewModel @Inject constructor(
	private val policyRepository: PolicyRepository
) : ViewModel() {

	val servicePolicyUiState = policyRepository.fetchServicePolicy()
		.map {
			when (it) {
				is Result.Success -> {
					val servicePolicyResult = it.body ?: ServicePolicyResult()
					val title = servicePolicyResult.title
					val content = servicePolicyResult.content
					ServicePolicyUiState.Success(title = title, content = content)
				}
				else -> ServicePolicyUiState.Failure("Network Fail")
			}
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000L),
			initialValue = ServicePolicyUiState.Loading
		)
}