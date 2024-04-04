package com.lyfe.android.feature.profileedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.CheckNicknameUseCase
import com.lyfe.android.core.domain.usecase.EditProfileUseCase
import com.lyfe.android.core.domain.usecase.GetImageUploadUrlUseCase
import com.lyfe.android.core.domain.usecase.GetUserInfoUseCase
import com.lyfe.android.core.domain.usecase.UploadImageUseCase
import com.lyfe.android.core.model.User
import com.lyfe.android.feature.nickname.ValidationTextUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

private const val NICKNAME_MAX_LENGTH = 10

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProfileEditViewModel @Inject constructor(
	private val getUserInfoUseCase: GetUserInfoUseCase,
	private val checkNicknameUseCase: CheckNicknameUseCase,
	private val getImageUploadUrlUseCase: GetImageUploadUrlUseCase,
	private val uploadImageUseCase: UploadImageUseCase,
	private val editProfileUseCase: EditProfileUseCase
) : ViewModel() {

	var uiState by mutableStateOf<ProfileEditUiState>(ProfileEditUiState.Loading)
		private set

	private val user = mutableStateOf(User())

	private val _nickname = MutableStateFlow("")
	val nickname get() = _nickname.value

	val nicknameValidationUiState = _nickname.flatMapMerge { nickname ->
		uiState = ProfileEditUiState.IDLE
		flowOf(
			ValidationTextUiState.Validation(
				nickName = nickname,
				containsTextWithNum = checkTextWithNum(),
				notContainsSpecialLetter = checkSpecialLetter(),
				notExceedMaxLength = checkExceedMaxLength()
			)
		)
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = ValidationTextUiState.Validation()
	)

	private val _profileImage = mutableStateOf("")
	val profileImage get() = _profileImage.value

	private val _imagePath = mutableStateOf<String?>(null) // 사용자가 실시간으로 수정한 프로필 이미지 경로값
	val imagePath get() = _imagePath.value

	init {
		getUserInfo()
	}

	private fun getUserInfo() {
		uiState = ProfileEditUiState.Loading
		viewModelScope.launch {
			getUserInfoUseCase().catch {
				uiState = ProfileEditUiState.Failure(
					message = it.message ?: "오류로 인해 닉네임 중복 검사에 실패했습니다."
				)
			}.collect {
				user.value = it
				_nickname.value = it.name
				_profileImage.value = it.profileImage
				uiState = ProfileEditUiState.IDLE
			}
		}
	}

	fun checkNicknameDuplicate() {
		if (user.value.name == nickname) {
			// 닉네임 변경하지 않았을 경우 바로 이미지 업로드 수행
			uploadProfileImage()
			return
		}
		uiState = ProfileEditUiState.Loading
		viewModelScope.launch {
			checkNicknameUseCase(nickname).catch {
				val message = it.message ?: "오류로 인해 닉네임 중복 검사에 실패했습니다."
				uiState = ProfileEditUiState.Failure(
					message = message
				)
			}.collectLatest {
				// 닉네임 중복 검사 문제 없으면 프로필 변경을 위해 이미지 업로드 실행
				uploadProfileImage()
			}
		}
	}

	private fun uploadProfileImage() {
		uiState = ProfileEditUiState.Loading
		viewModelScope.launch {
			val file = imagePath?.let { File(it) }
			if (file == null) {
				editProfile()
				return@launch
			}
			getImageUploadUrlUseCase(file.getImageFormat(), "topic_picture").catch {
				val message = it.message ?: "오류로 인해 프로필 변경에 실패했습니다."
				uiState = ProfileEditUiState.Failure(message)
			}.collectLatest {
				val url = it.url
				when (val response = uploadImageUseCase(url, it.key, file)) {
					is Result.Success -> {
						_profileImage.value = url
						editProfile()
					}
					is Result.Failure -> {
						uiState = ProfileEditUiState.Failure(response.error ?: "프로필 이미지 변경에 실패했습니다.")
					}
					is Result.NetworkError -> {
						uiState = ProfileEditUiState.Failure(response.exception.localizedMessage ?: "프로필 이미지 변경에 실패했습니다.")
					}
					is Result.Unexpected -> {
						uiState = ProfileEditUiState.Failure(response.t?.localizedMessage ?: "프로필 이미지 변경에 실패했습니다.")
					}
				}
			}
		}
	}

	private fun editProfile() = viewModelScope.launch {
		editProfileUseCase(
			nickname = nickname,
			profileUrl = _profileImage.value
		).catch {
			val message = it.message ?: "오류로 인해 프로필 변경에 실패하였습니다."
			uiState = ProfileEditUiState.Failure(
				message = message
			)
		}.collect {
			uiState = ProfileEditUiState.Success
		}
	}

	fun setNickname(nickname: String) {
		_nickname.value = nickname
	}

	fun isValidNickname(): Boolean {
		return checkTextWithNum() && checkExceedMaxLength() && checkSpecialLetter()
	}

	private fun checkTextWithNum(): Boolean {
		val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d).+\$")
		return nickname.matches(regex = regex)
	}

	private fun checkExceedMaxLength(): Boolean {
		return nickname.length <= NICKNAME_MAX_LENGTH
	}

	private fun checkSpecialLetter(): Boolean {
		val regex = Regex("[,=':;><?/~`_.!@#^&*]|\\\\[|\\\\]")
		return nickname.replace(regex, "") == nickname
	}

	fun updateProfileImageFilePath(path: String) {
		_imagePath.value = path
	}
}