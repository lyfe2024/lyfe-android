package com.lyfe.android.feature.profileedit

import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.domain.usecase.CheckNicknameUseCase
import com.lyfe.android.core.domain.usecase.EditProfileUseCase
import com.lyfe.android.core.domain.usecase.GetImageUploadUrlUseCase
import com.lyfe.android.core.domain.usecase.GetUserInfoUseCase
import com.lyfe.android.core.domain.usecase.UploadImageUseCase
import com.lyfe.android.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
	private val getUserInfoUseCase: GetUserInfoUseCase,
	private val checkNicknameUseCase: CheckNicknameUseCase,
	private val getImageUploadUrlUseCase: GetImageUploadUrlUseCase,
	private val uploadImageUseCase: UploadImageUseCase,
	private val editProfileUseCase: EditProfileUseCase
) : ViewModel() {

	private val maxLength = 10

	var uiState by mutableStateOf<ProfileEditUiState>(ProfileEditUiState.Loading)
		private set

	private val _user = MutableStateFlow(User())
	val user get() = _user.value

	private val _path = mutableStateOf<String?>(null)
	val path get() = _path.value

	init {
		getUserInfo()
	}

	private fun getUserInfo() = viewModelScope.launch {
		getUserInfoUseCase().onEach {
			uiState = ProfileEditUiState.Loading
		}.catch {
			uiState = ProfileEditUiState.Failure(
				message = it.message ?: "오류로 인해 닉네임 중복 검사에 실패했습니다."
			)
		}.collect {
			_user.value = it
			_path.value = it.profileImage
			uiState = ProfileEditUiState.IDLE
		}
	}

	fun checkNicknameDuplicate(nickname: String) = viewModelScope.launch {
		checkNicknameUseCase(nickname = nickname).onEach {
			uiState = ProfileEditUiState.Loading
		}.catch {
			val message = it.message ?: "오류로 인해 닉네임 중복 검사에 실패했습니다."
			uiState = ProfileEditUiState.Failure(
				message = message
			)
			LogUtil.e("CheckNicknameError", message)
		}.collect {
			uiState = ProfileEditUiState.IDLE
			editProfile(
				nickname = nickname,
				profileUrl = user.profileImage,
				width = 120,
				height = 120
			)
		}
	}

	fun uploadProfileImage() = viewModelScope.launch {
		val file = path?.let { File(it) }
		val format = file?.getImageFormat()
		getImageUploadUrlUseCase(format ?: "", "topic_picture").onEach {
			uiState = ProfileEditUiState.Loading
		}.catch {
			val message = it.message ?: "오류로 인해 프로필 변경에 실패하였습니다."
			uiState = ProfileEditUiState.Failure(
				message = message
			)
		}.collect {
			uploadImageUseCase(it.url, it.key, file!!)
		}
	}

	private fun editProfile(
		nickname: String,
		profileUrl: String,
		width: Int,
		height: Int
	) = viewModelScope.launch {
		editProfileUseCase(
			nickname = nickname,
			profileUrl = profileUrl,
			width = width,
			height = height
		).onEach {
			uiState = ProfileEditUiState.Loading
		}.catch {
			val message = it.message ?: "오류로 인해 프로필 변경에 실패하였습니다."
			uiState = ProfileEditUiState.Failure(
				message = message
			)
		}.collect {
			_user.value = it
			uiState = ProfileEditUiState.Success(user)
		}
	}

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

	fun updateProfileImageFilePath(path: String) {
		_path.value = path
	}

	private fun File.getImageFormat(): String {
		if (!exists()) {
			throw NoSuchFileException(this, null, "The File is not existed")
		}
		val options = BitmapFactory.Options()
		options.inJustDecodeBounds = true
		BitmapFactory.decodeFile(path, options)
		val mimeType = options.outMimeType
		val index = mimeType.indexOf('/')
		return options.outMimeType.substring(index + 1)
	}
}