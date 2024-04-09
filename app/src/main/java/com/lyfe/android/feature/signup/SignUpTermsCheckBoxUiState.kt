package com.lyfe.android.feature.signup

sealed interface SignUpTermsCheckBoxUiState {

	data class Checked(
		val servicePolicy: Boolean = false,
		val userInfoPolicy: Boolean = false
	) : SignUpTermsCheckBoxUiState {

		fun validation() = servicePolicy && userInfoPolicy
	}
}