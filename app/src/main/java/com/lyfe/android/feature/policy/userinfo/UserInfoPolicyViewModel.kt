package com.lyfe.android.feature.policy.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.PolicyRepository
import com.lyfe.android.core.model.UserInfoPolicyResult
import com.lyfe.android.feature.policy.UserInfoPolicyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserInfoPolicyViewModel @Inject constructor(
	private val policyRepository: PolicyRepository
) : ViewModel() {

	val userInfoPolicyUiState = policyRepository.fetchUserInfoPolicy()
		.map {
			when (it) {
				is Result.Success -> {
					val servicePolicyResult = it.body ?: UserInfoPolicyResult()
					val title = servicePolicyResult.title
					val content = servicePolicyResult.content
					UserInfoPolicyUiState.Success(title = title, content = content)
				}
				else -> UserInfoPolicyUiState.Failure("Network Fail")
			}
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000L),
			initialValue = UserInfoPolicyUiState.Loading
		)
}