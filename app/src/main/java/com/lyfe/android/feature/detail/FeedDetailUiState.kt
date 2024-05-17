package com.lyfe.android.feature.detail

import com.lyfe.android.core.model.BoardDetail
import com.lyfe.android.core.model.Comment

sealed interface FeedDetailUiState {

	data class Success(
		val boardDetail: BoardDetail,
		val commentList: List<Comment>
	) : FeedDetailUiState

	object Loading : FeedDetailUiState

	data class Error(val message: String?) : FeedDetailUiState
}