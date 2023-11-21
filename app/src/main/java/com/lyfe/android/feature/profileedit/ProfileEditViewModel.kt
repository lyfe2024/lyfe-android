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

	fun getNicknameInvalidReason(text: String): List<NicknameFormState> {
//		val regex = Regex("^[\\s가-힣a-zA-Z0-9]{1,10}$")
		val regex1 = Regex("^[a-zA-Z]+[0-9]+\$")
		val regex2 = Regex("^[0-9]+[a-zA-Z]+\$")
		val specialCharRegex = Regex("[,=':;><?/~`_.!@#^&*]|\\\\[|\\\\]")
		val list = mutableListOf<NicknameFormState>()

		if (text.length > maxLength) {
			list.add(NicknameFormState.NICKNAME_FORM_TOO_LONG)
		}
		if (text != text.replace(specialCharRegex, "")) {
			list.add(NicknameFormState.CONTAIN_SPECIAL_CHAR)
		}
		if (!text.matches(regex1) && !text.matches(regex2)) {
			list.add(NicknameFormState.NEED_LETTER_NUMBER_COMBINATION)
		}

		return list
	}
}