package com.lyfe.android.feature.post.create.text

data class CreateTextPostUiState(
	val title: String = "",
	val content: String = "",
	val event: CreateTextPostUiEvent = CreateTextPostUiEvent.IDLE
) {
	fun isPostValidation() = title.isNotEmpty() && content.isNotEmpty()
}

sealed interface CreateTextPostUiEvent {
	object IDLE : CreateTextPostUiEvent

	object MoveToSelectAlbum : CreateTextPostUiEvent

	data class CreateSuccess(
		val boardId: Long
	) : CreateTextPostUiEvent

	data class CreateFail(
		val message: String?
	) : CreateTextPostUiEvent
}