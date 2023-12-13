package com.lyfe.android.feature.profileedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
	private val userRepository: UserRepository
) : ViewModel() {

	private val maxLength = 10

	var uiState by mutableStateOf<ProfileEditUiState>(ProfileEditUiState.IDLE)
		private set

	fun isNicknameTooLong(text: String): NicknameInvalidState {
		return if (text.isEmpty()) {
			NicknameInvalidState.EMPTY
		} else if (text.length > maxLength) {
			NicknameInvalidState.INCORRECT
		} else {
			NicknameInvalidState.CORRECT
		}
	}

	fun isNicknameHasSpecialLetter(text: String): NicknameInvalidState {
		val regex = Regex("[,=':;><?/~`_.!@#^&*]|\\\\[|\\\\]")
		return if (text.isEmpty()) {
			NicknameInvalidState.EMPTY
		} else if (text != text.replace(regex, "")) {
			NicknameInvalidState.INCORRECT
		} else {
			NicknameInvalidState.CORRECT
		}
	}

	fun isNicknameCombinationWrong(text: String): NicknameInvalidState {
		val regex = Regex("^[가-힣a-zA-Z0-9]+\$")
		return if (text.isEmpty()) {
			NicknameInvalidState.EMPTY
		} else if (!text.matches(regex)) {
			NicknameInvalidState.INCORRECT
		} else {
			NicknameInvalidState.CORRECT
		}
	}

	suspend fun checkNicknameDuplicate(nickname: String) {
		viewModelScope.launch {
			val result = userRepository.fetchIsNicknameDuplicated(nickname = nickname)
			when (result) {
				is Result.Success -> {
					uiState = ProfileEditUiState.Success(nickname = nickname)
				}
				is Result.Failure -> {
					uiState = ProfileEditUiState.Failure(
						message = result.error ?: "오류로 인해 닉네임 중복 검사에 실패했습니다."
					)
				}
				is Result.NetworkError -> {
					uiState = ProfileEditUiState.Failure(
						message = "네트워크 연결에 실패했습니다."
					)
				}
				is Result.Unexpected -> {
					uiState = ProfileEditUiState.Failure(
						message = "예기치 못한 오류가 발생했습니다."
					)
				}
			}
		}
	}
}