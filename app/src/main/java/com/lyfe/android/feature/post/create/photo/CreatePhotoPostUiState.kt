package com.lyfe.android.feature.post.create.photo

import com.lyfe.android.core.common.ui.permission.NeededPermission
import javax.annotation.concurrent.Immutable

@Immutable
data class CreatePhotoPostUiState(
	val selectedImage: String = "",
	val title: String = "",
	val event: CreatePhotoPostUiEvent = CreatePhotoPostUiEvent.IDLE
) {
	fun isAvailableToRegisterData() = selectedImage.isNotEmpty() && titleValidation()

	private fun titleValidation() = title.isNotEmpty() && title.length <= MAX_TITLE_LENGTH

	companion object {
		const val MAX_TITLE_LENGTH = 20
	}
}

sealed interface CreatePhotoPostUiEvent {
	object IDLE : CreatePhotoPostUiEvent

	object Loading : CreatePhotoPostUiEvent

	data class Failure(
		val message: String?
	) : CreatePhotoPostUiEvent

	data class CreateSuccess(
		val id: Long
	) : CreatePhotoPostUiEvent

	object CheckPermission : CreatePhotoPostUiEvent

	data class ShowPermissionAlertDialog(
		val failedPermissionList: List<NeededPermission>
	) : CreatePhotoPostUiEvent

	object MoveToSelectAlbum : CreatePhotoPostUiEvent
}