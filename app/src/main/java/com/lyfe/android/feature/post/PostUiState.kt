package com.lyfe.android.feature.post

import com.lyfe.android.core.common_ui.permission.NeededPermission
import javax.annotation.concurrent.Immutable

sealed interface PostUiState {
	@Immutable
	data class Success(
		val selectedImage: String = "",
		val event: PostUiEvent = PostUiEvent.IDLE
	) : PostUiState
}

sealed interface PostUiEvent {
	object IDLE: PostUiEvent

	object CheckPermission : PostUiEvent

	data class ShowPermissionAlertDialog(
		val failedPermissionList: List<NeededPermission>
	) : PostUiEvent

	object MoveToSelectAlbum: PostUiEvent
}