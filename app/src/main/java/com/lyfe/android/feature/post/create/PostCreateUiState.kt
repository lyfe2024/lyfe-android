package com.lyfe.android.feature.post.create

import com.lyfe.android.core.common.ui.permission.NeededPermission
import javax.annotation.concurrent.Immutable

sealed interface PostCreateUiState {
	@Immutable
	data class Success(
		val selectedImage: String = "",
		val event: PostCreateUiEvent = PostCreateUiEvent.IDLE
	) : PostCreateUiState
}

sealed interface PostCreateUiEvent {
	object IDLE : PostCreateUiEvent

	object CheckPermission : PostCreateUiEvent

	data class ShowPermissionAlertDialog(
		val failedPermissionList: List<NeededPermission>
	) : PostCreateUiEvent

	object MoveToSelectAlbum : PostCreateUiEvent
}