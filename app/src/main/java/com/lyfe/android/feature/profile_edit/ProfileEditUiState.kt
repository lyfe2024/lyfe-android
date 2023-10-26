package com.lyfe.android.feature.profile_edit

sealed interface ProfileEditUiState {

	object Loading : ProfileEditUiState

	data class Success(
		val thumbnail: String?,
		val nickname: String
	): ProfileEditUiState

	object Failure : ProfileEditUiState

}