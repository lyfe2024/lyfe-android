package com.lyfe.android.feature.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NICKNAME_MAX_LENGTH = 10

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CreateNicknameViewModel @Inject constructor(
	private val userRepository: UserRepository
) : ViewModel() {

	private val _createNicknameUiState = MutableStateFlow<CreateNicknameUiState>(CreateNicknameUiState.IDLE)
	val createNicknameUiState = _createNicknameUiState.asStateFlow()

	private val nicknameFlow = MutableStateFlow("")
	private val checkNicknameDuplicate = MutableStateFlow(false)
	val nicknameValidationUiState = nicknameFlow.flatMapMerge { nickname ->
		_createNicknameUiState.value = CreateNicknameUiState.IDLE
		flowOf(
			ValidationTextUiState.Validation(
				nickName = nickname,
				containsTextWithNum = checkTextWithNum(nickname),
				notContainsSpecialLetter = checkSpecialLetter(nickname),
				notExceedMaxLength = checkExceedMaxLength(nickname),
			)
		)
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = ValidationTextUiState.Validation()
	)

	private fun checkTextWithNum(text: String): Boolean {
		val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d).+\$")
		return text.matches(regex = regex)
	}

	private fun checkExceedMaxLength(text: String): Boolean {
		return text.length <= NICKNAME_MAX_LENGTH
	}

	private fun checkSpecialLetter(text: String): Boolean {
		val regex = Regex("[,=':;><?/~`_.!@#^&*]|\\\\[|\\\\]")
		return text.replace(regex, "") == text
	}

	fun setNickName(nickName: String) {
		checkNicknameDuplicate.value = false
		this.nicknameFlow.value = nickName
	}

	fun checkNicknameDuplicate() {
//		_createNicknameUiState.value = CreateNicknameUiState.Loading
		_createNicknameUiState.value = CreateNicknameUiState.Duplicated

		viewModelScope.launch {
			val result = userRepository.fetchIsNicknameDuplicated(nickname = nicknameFlow.value)
			if (result is Result.Success) {
				_createNicknameUiState.value = CreateNicknameUiState.Duplicated
				checkNicknameDuplicate.value = !(result.body?.result?.isAvailable ?: true)
			}
		}
	}

//	fun checkNicknameDuplicate(nickname: String) = viewModelScope.launch {
//		val result = userRepository.fetchIsNicknameDuplicated(nickname = nickname)
//		when (result) {
//			is Result.Success -> {
//				uiState = ProfileEditUiState.Success(nickname = nickname)
//			}
//
//			is Result.Failure -> {
//				uiState = ProfileEditUiState.Failure(
//					message = result.error ?: "오류로 인해 닉네임 중복 검사에 실패했습니다."
//				)
//			}
//
//			is Result.NetworkError -> {
//				uiState = ProfileEditUiState.Failure(
//					message = "네트워크 연결에 실패했습니다."
//				)
//			}
//
//			is Result.Unexpected -> {
//				uiState = ProfileEditUiState.Failure(
//					message = "예기치 못한 오류가 발생했습니다."
//				)
//			}
//		}
//	}
}