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

	fun checkNicknameForm(text: String): NicknameFormState {
		val regex = Regex("^[\\s가-힣a-zA-Z0-9]{1,10}$")
		val specialCharRegex = Regex("[,=':;><?/~`_.!@#^&*]|\\\\[|\\\\]")
		return if (text.matches(regex = regex)) {
			NicknameFormState.CORRECT
		} else if (text.length > maxLength) {
			NicknameFormState.NICKNAME_FORM_TOO_LONG
		} else if (text != text.replace(specialCharRegex, "")) {
			NicknameFormState.CONTAIN_SPECIAL_CHAR
		} else {
			NicknameFormState.NEED_LETTER_NUMBER_COMBINATION
		}
	}
}