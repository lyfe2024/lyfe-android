package com.lyfe.android.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.GetLatestBoardsUseCase
import com.lyfe.android.core.domain.usecase.GetTodayTopicUseCase
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val getTodayTopicUseCase: GetTodayTopicUseCase,
	private val getLatestBoardsUseCase: GetLatestBoardsUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Success)
	val uiState get() = _uiState.asStateFlow()

	private val _imageFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val imageFeedList get() = _imageFeedList.asStateFlow()

	private val _textFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val textFeedList get() = _textFeedList.asStateFlow()

	private var textFeedCursorId by mutableLongStateOf(0)

	private var imageFeedCursorId by mutableLongStateOf(0)

	var todayTopic by mutableStateOf("오늘의 주제")
		private set

	init {
		getTodayTopic()
	}

	private fun getTodayTopic() = viewModelScope.launch {
		when (val result = getTodayTopicUseCase()) {
			is Result.Success -> {
				todayTopic = result.body.content
			}
			else -> {
				// TODO 토픽 실패 처리
			}
		}
	}

	fun fetchLatestFeedList(feedType: FeedType) {
		val cursorId = when (feedType) {
			FeedType.BOARD -> textFeedCursorId
			FeedType.BOARD_PICTURE -> imageFeedCursorId
		}

		viewModelScope.launch {
			getLatestBoardsUseCase(
				cursorId = cursorId,
				boardType = feedType.name
			).catch { t ->
				_uiState.update { HomeUiState.Failure(t.message ?: "") }
			}.collect {
				when (feedType) {
					FeedType.BOARD -> {
						_textFeedList.compareAndSet(_textFeedList.value, it.subList(0, minOf(it.size, 10)))
						textFeedCursorId = if (it.isNotEmpty()) {
							it.last().feedId
						} else {
							textFeedCursorId
						}
					}
					FeedType.BOARD_PICTURE -> {
						_imageFeedList.compareAndSet(_imageFeedList.value, it.subList(0, minOf(it.size, 10)))
						imageFeedCursorId = if (it.isNotEmpty()) {
							it.last().feedId
						} else {
							imageFeedCursorId
						}
					}
				}
			}
		}
	}

	private val fakeFeeds = listOf(
		Feed(
			feedId = 1L,
			title = "제목1",
			content = "내용1",
			feedImageUrl = "https://picsum.photos/300/450",
			date = "날짜",
			userId = 1L,
			userName = "유저 닉네임1",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 2L,
			title = "제목2",
			content = "내용2",
			feedImageUrl = "https://picsum.photos/300/451",
			date = "날짜",
			userId = 2L,
			userName = "유저 닉네임2",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 3L,
			title = "제목3",
			content = "내용3",
			feedImageUrl = "https://picsum.photos/300/453",
			date = "날짜",
			userId = 3L,
			userName = "유저 닉네임3",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 4L,
			title = "제목4",
			content = "내용4",
			feedImageUrl = "https://picsum.photos/300/454",
			date = "날짜",
			userId = 4L,
			userName = "유저 닉네임4",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 5L,
			title = "제목5",
			content = "내용5",
			feedImageUrl = "https://picsum.photos/300/455",
			date = "날짜",
			userId = 5L,
			userName = "유저 닉네임5",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 6L,
			title = "제목6",
			content = "내용6",
			feedImageUrl = "https://picsum.photos/300/456",
			date = "날짜",
			userId = 1L,
			userName = "유저 닉네임1",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 7L,
			title = "제목7",
			content = "내용7",
			feedImageUrl = "https://picsum.photos/300/457",
			date = "날짜",
			userId = 2L,
			userName = "유저 닉네임2",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 8L,
			title = "제목8",
			content = "내용8",
			feedImageUrl = "https://picsum.photos/300/458",
			date = "날짜",
			userId = 3L,
			userName = "유저 닉네임3",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 9L,
			title = "제목9",
			content = "내용9",
			feedImageUrl = "https://picsum.photos/300/459",
			date = "날짜",
			userId = 4L,
			userName = "유저 닉네임4",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		),
		Feed(
			feedId = 10L,
			title = "제목10",
			content = "내용10",
			feedImageUrl = "https://picsum.photos/300/460",
			date = "날짜",
			userId = 5L,
			userName = "유저 닉네임5",
			userProfileImgUrl = "https://picsum.photos/100",
			whiskyCount = 23,
			commentCount = 12,
			isLike = false
		)
	)
}