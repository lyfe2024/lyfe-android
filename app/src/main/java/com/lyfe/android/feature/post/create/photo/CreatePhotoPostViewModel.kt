package com.lyfe.android.feature.post.create.photo

import android.os.Build
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lyfe.android.core.common.ui.permission.NeededPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val SAVED_TITLE_KEY = "saved_title_key"

@HiltViewModel
class CreatePhotoPostViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val _uiState = MutableStateFlow(CreatePhotoPostUiState())
	val uiState get() = _uiState.asStateFlow()

	private val passedPermissionSet = mutableSetOf<NeededPermission>()
	val neededPermissions = getNeededPermission()

	init {
		val savedTitle = savedStateHandle[SAVED_TITLE_KEY] ?: ""
		_uiState.update {
			uiState.value.copy(
				title = savedTitle
			)
		}
	}

	fun saveSelectedImage(imageUrl: String) {
		_uiState.update {
			uiState.value.copy(
				selectedImage = imageUrl
			)
		}
	}

	fun savePostTitle(title: String) {
		if (!titleValidation(title)) return

		savedStateHandle[SAVED_TITLE_KEY] = title

		_uiState.update {
			uiState.value.copy(
				title = title
			)
		}
	}

	private fun titleValidation(title: String) = title.length <= 20

	private fun getNeededPermission(): Array<String> {
		val permissionList = mutableListOf<String>()

		// SDK 13부터 이미지 및 사진에 대한 세부 권한 추가 요청 필요
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			permissionList.add(NeededPermission.READ_MEDIA_IMAGES.permission)
		} else {
			permissionList.add(NeededPermission.READ_EXTERNAL_STORAGE.permission)
		}

		return permissionList.toTypedArray()
	}

	fun checkPermission() {
		_uiState.update {
			uiState.value.copy(
				event = CreatePhotoPostUiEvent.CheckPermission
			)
		}
	}

	fun checkPermissionResult(
		passedPermissionList: List<NeededPermission>,
		failedPermissionList: List<NeededPermission>
	) {
		this.passedPermissionSet.addAll(passedPermissionList)

		val event = if (checkPassedAllPermissions()) {
			CreatePhotoPostUiEvent.MoveToSelectAlbum
		} else {
			CreatePhotoPostUiEvent.ShowPermissionAlertDialog(
				failedPermissionList = failedPermissionList
			)
		}

		_uiState.update {
			uiState.value.copy(
				event = event
			)
		}
	}

	fun addAllowedPermissions(passedPermissionList: List<NeededPermission>) {
		this.passedPermissionSet.addAll(passedPermissionList)

		val event = if (checkPassedAllPermissions()) {
			CreatePhotoPostUiEvent.MoveToSelectAlbum
		} else {
			CreatePhotoPostUiEvent.IDLE
		}
		_uiState.value = CreatePhotoPostUiState(event = event)
	}

	fun setUiEventIdle() {
		_uiState.update {
			uiState.value.copy(
				event = CreatePhotoPostUiEvent.IDLE
			)
		}
	}

	override fun onCleared() {
		super.onCleared()
		Log.e("Test@@@", "ViewModel Clear")
	}

	private fun checkPassedAllPermissions() =
		passedPermissionSet.size == neededPermissions.size
}