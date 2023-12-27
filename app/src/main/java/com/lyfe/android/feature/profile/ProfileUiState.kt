package com.lyfe.android.feature.profile

sealed interface ProfileUiState {

	object GuestSuccess : ProfileUiState

	object UserSuccess : ProfileUiState

	object Loading : ProfileUiState

	object Failure : ProfileUiState
}