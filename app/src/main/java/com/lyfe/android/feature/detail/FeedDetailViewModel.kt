package com.lyfe.android.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.model.zip
import com.lyfe.android.core.domain.usecase.GetBoardDetailUseCase
import com.lyfe.android.core.domain.usecase.GetCommentsUseCase
import com.lyfe.android.core.model.BoardDetail
import com.lyfe.android.core.model.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * 고려해야할 상태
 * 1. 추후 스와이프를 통해 리프레쉬될 수 있는 구조
 * 2. comment가 cursor기반 스크롤 데이터 로딩이 가능하게 끔 만들기.
 */

/**
 * 리프레쉬가 되게 끔 하는 방법
 * 1. comment로 변환
 */

data class FeedDetail(
	val boardDetail: BoardDetail,
	val commentList: List<Comment>
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FeedDetailViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val getBoardDetailUseCase: GetBoardDetailUseCase,
	private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

	private val boardId = savedStateHandle.getStateFlow<Long?>("boardId", 500L)
	private val fetchingCommentId = MutableStateFlow(0L)

	private val commentList = mutableListOf<Comment>()

	private var _commentsLoading = MutableStateFlow(false)
	val commentsLoading = _commentsLoading.asStateFlow()

	val feedDetailUiState = boardId.flatMapLatest { boardId ->
		if (boardId == null) {
			flowOf(FeedDetailUiState.Error("boardId is Null"))
		} else {
			val boardFlow = getBoardDetailUseCase(boardId = boardId)

			val commentsFlow = fetchingCommentId.flatMapLatest { _ ->
				getCommentsUseCase(
					boardId = boardId,
					lastCommentId = 0
				)
			}

			combine(boardFlow, commentsFlow) { boardResult, commentsResult ->
				Pair(boardResult, commentsResult)
			}.map {
				val result = it.first.zip(it.second) { board, comments ->
					commentList.addAll(comments)
					FeedDetail(boardDetail = board, commentList = commentList.toList())
				}

				when (result) {
					is Result.Success -> {
						_commentsLoading.value = false

						FeedDetailUiState.Success(
							boardDetail = result.body.boardDetail,
							commentList = result.body.commentList
						)
					}

					is Result.Unexpected -> {
						FeedDetailUiState.Error(result.t.message)
					}

					is Result.Failure -> {
						FeedDetailUiState.Error("${result.code} ${result.error}")
					}

					is Result.NetworkError -> {
						FeedDetailUiState.Error("${result.exception.message}")
					}
				}
			}
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = FeedDetailUiState.Loading
	)

	fun fetchingCommentList() {
		if (!_commentsLoading.value) {
			_commentsLoading.value = true
			fetchingCommentId.value += 1
		}
	}
}