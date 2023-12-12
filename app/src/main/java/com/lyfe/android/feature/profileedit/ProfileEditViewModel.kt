package com.lyfe.android.feature.profileedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor() : ViewModel() {

	private val maxLength = 10

	var uiState by mutableStateOf<ProfileEditUiState>(ProfileEditUiState.Loading)
		private set

	init {
		viewModelScope.launch {
			uiState = ProfileEditUiState.Success(nickname = "Guest")
		}
	}

	fun isNicknameTooLong(text: String): NicknameInvalidState {
		return if (text.isEmpty())
			NicknameInvalidState.EMPTY
		else if (text.length > maxLength)
			NicknameInvalidState.INCORRECT
		else
			NicknameInvalidState.CORRECT
	}

	fun isNicknameHasSpecialLetter(text: String): NicknameInvalidState {
		val regex = Regex("[,=':;><?/~`_.!@#^&*]|\\\\[|\\\\]")
		return if (text.isEmpty())
			NicknameInvalidState.EMPTY
		else if (text != text.replace(regex, ""))
			NicknameInvalidState.INCORRECT
		else
			NicknameInvalidState.CORRECT
	}

	fun isNicknameCombinationWrong(text: String): NicknameInvalidState {
		val regex = Regex("^[가-힣a-zA-Z0-9]+\$")
		return if (text.isEmpty())
			NicknameInvalidState.EMPTY
		else if (!text.matches(regex))
			NicknameInvalidState.INCORRECT
		else NicknameInvalidState.CORRECT
	}
}