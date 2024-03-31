package com.lyfe.android.feature.policy

sealed interface SignUpTermsPolicyCheckBoxUiState {

	data class Checked(
		val servicePolicy: Boolean = false,
		val userInfoPolicy: Boolean = false
	) : SignUpTermsPolicyCheckBoxUiState {

		fun validation() = servicePolicy && userInfoPolicy
	}
}