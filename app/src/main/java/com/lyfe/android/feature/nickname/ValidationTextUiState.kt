package com.lyfe.android.feature.nickname

sealed interface ValidationTextUiState {

	data class Validation(
		val nickName: String = "",
		private val containsTextWithNum: Boolean = false,
		private val notContainsSpecialLetter: Boolean = false,
		private val notExceedMaxLength: Boolean = false,
	) : ValidationTextUiState {
		fun checkTextWithNum() = nickName.isNotEmpty() && containsTextWithNum
		fun checkNotSpecialLetter() = nickName.isNotEmpty() && notContainsSpecialLetter
		fun checkNotExceedMaxLength() = nickName.isNotEmpty() && notExceedMaxLength

		fun checkValidationSuccess() =
			nickName.isNotEmpty() && containsTextWithNum && notContainsSpecialLetter && notExceedMaxLength
	}
}