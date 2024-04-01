package com.lyfe.android.feature.policy.userinfo

sealed interface UserInfoPolicyUiState {

	object Loading : UserInfoPolicyUiState

	data class Success(
		val title: String,
		val content: String
	) : UserInfoPolicyUiState

	data class Failure(
		val message: String
	) : UserInfoPolicyUiState
}