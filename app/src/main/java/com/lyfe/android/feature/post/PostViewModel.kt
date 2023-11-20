package com.lyfe.android.feature.post

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lyfe.android.core.common.ui.permission.NeededPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor() : ViewModel() {

	var uiState by mutableStateOf<PostUiState>(PostUiState.Success())
		private set

	var title by mutableStateOf("")

	private val passedPermissionSet = mutableSetOf<NeededPermission>()
	val neededPermissions = getNeededPermission()

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
		uiState = PostUiState.Success(event = PostUiEvent.CheckPermission)
	}

	fun checkPermissionResult(
		passedPermissionList: List<NeededPermission>,
		failedPermissionList: List<NeededPermission>
	) {
		this.passedPermissionSet.addAll(passedPermissionList)

		val event = if (checkPassedAllPermissions()) {
			PostUiEvent.MoveToSelectAlbum
		} else {
			PostUiEvent.ShowPermissionAlertDialog(
				failedPermissionList = failedPermissionList
			)
		}

		uiState = PostUiState.Success(event = event)
	}

	fun addAllowedPermissions(passedPermissionList: List<NeededPermission>) {
		this.passedPermissionSet.addAll(passedPermissionList)

		val event = if (checkPassedAllPermissions()) {
			PostUiEvent.MoveToSelectAlbum
		} else {
			PostUiEvent.IDLE
		}
		uiState = PostUiState.Success(event = event)
	}

	fun setUiEventIdle() {
		uiState = PostUiState.Success(event = PostUiEvent.IDLE)
	}

	private fun checkPassedAllPermissions() =
		passedPermissionSet.size == neededPermissions.size

	fun setSelectedImage(selectedImage: String) {
		uiState = PostUiState.Success(selectedImage = selectedImage)
	}
}