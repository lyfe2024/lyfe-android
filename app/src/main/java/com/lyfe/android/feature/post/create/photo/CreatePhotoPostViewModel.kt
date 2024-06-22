package com.lyfe.android.feature.post.create.photo

import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.common.ui.permission.NeededPermission
import com.lyfe.android.core.data.network.model.onSuccess
import com.lyfe.android.core.domain.usecase.board.RegisterLocalImageBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVED_TITLE_KEY = "saved_title_key"

@HiltViewModel
class CreatePhotoPostViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val registerLocalImageBoardUseCase: RegisterLocalImageBoardUseCase
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

	fun registerBoard() {
		if (!uiState.value.isAvailableToRegisterData()) return

		viewModelScope.launch {
			registerLocalImageBoardUseCase(
				title = uiState.value.title,
				localContentImageUrl = uiState.value.selectedImage
			).onStart {
				_uiState.compareAndSet(
					uiState.value,
					uiState.value.copy(event = CreatePhotoPostUiEvent.Loading)
				)
			}.catch { throwable ->
				_uiState.update { uiState.value.copy(event = CreatePhotoPostUiEvent.Failure(throwable.message)) }
			}.collectLatest {
				it.onSuccess { registerBoardData ->
					_uiState.update {
						uiState.value.copy(
							event = CreatePhotoPostUiEvent.CreateSuccess(
								id = registerBoardData.id
							)
						)
					}
				}
			}
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

	private fun titleValidation(title: String) = title.length <= CreatePhotoPostUiState.MAX_TITLE_LENGTH

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

	private fun checkPassedAllPermissions() =
		passedPermissionSet.size == neededPermissions.size
}