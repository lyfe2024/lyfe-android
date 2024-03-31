package com.lyfe.android.feature.policy

sealed interface ServicePolicyUiState {

	object Loading : ServicePolicyUiState

	data class Success(
		val title: String,
		val content: String
	) : ServicePolicyUiState

	data class Failure(
		val message: String
	) : ServicePolicyUiState
}