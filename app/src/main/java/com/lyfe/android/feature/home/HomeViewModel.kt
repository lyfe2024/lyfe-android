package com.lyfe.android.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.usecase.GetLatestBoardsUseCase
import com.lyfe.android.core.domain.usecase.GetPopularBoardsUseCase
import com.lyfe.android.core.domain.usecase.GetTodayTopicUseCase
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedFetchingType
import com.lyfe.android.core.model.FeedType
import com.lyfe.android.core.model.PopularType
import com.lyfe.android.feature.home.model.HomeFeedType
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
	private val getLatestBoardsUseCase: GetLatestBoardsUseCase,
	private val getPopularBoardsUseCase: GetPopularBoardsUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Success)
	val uiState get() = _uiState.asStateFlow()

	var homeFeedType by mutableStateOf(HomeFeedType.TODAY_TOPIC)
		private set

	private val _feedFetchingType = MutableStateFlow(FeedFetchingType.LATEST)
	val feedFetchingType get() = _feedFetchingType.asStateFlow()

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

	fun changeFilterType() {
		homeFeedType = if (homeFeedType == HomeFeedType.TODAY_TOPIC) {
			HomeFeedType.PAST_BEST
		} else {
			HomeFeedType.TODAY_TOPIC
		}
		resetData()
	}

	fun updateFeedFetchingType(fetchingType: FeedFetchingType) {
		if (feedFetchingType.value == fetchingType) {
			return
		}
		_feedFetchingType.value = fetchingType
		resetData()
	}

	fun fetchLatestFeedList(feedType: FeedType) {
		if (_uiState.value != HomeUiState.Loading) {
			val cursorId = when (feedType) {
				FeedType.BOARD -> textFeedCursorId
				FeedType.BOARD_PICTURE -> imageFeedCursorId
			}
			_uiState.update {
				HomeUiState.Loading
			}
			viewModelScope.launch {
				_textFeedList.compareAndSet(_textFeedList.value, fakeFeeds)
				_imageFeedList.compareAndSet(_imageFeedList.value, fakeFeeds)
				_uiState.update { HomeUiState.Success }
//				getLatestBoardsUseCase(
//					cursorId = cursorId,
//					boardType = feedType.name
//				).catch { t ->
//					_uiState.update { HomeUiState.Failure(t.message ?: "") }
//				}.collect {
//					when (feedType) {
//						FeedType.BOARD -> {
//							_textFeedList.compareAndSet(_textFeedList.value, _textFeedList.value + it)
//							textFeedCursorId = if (it.isNotEmpty()) {
//								it.last().feedId
//							} else {
//								textFeedCursorId
//							}
//						}
//						FeedType.BOARD_PICTURE -> {
//							_imageFeedList.compareAndSet(_imageFeedList.value, _imageFeedList.value + it)
//							imageFeedCursorId = if (it.isNotEmpty()) {
//								it.last().feedId
//							} else {
//								imageFeedCursorId
//							}
//						}
//					}
//					_uiState.update { HomeUiState.Success }
//				}
			}
		}
	}

	fun fetchPopularFeedList(feedType: FeedType) {
		if (_uiState.value != HomeUiState.Loading) {
			val cursorId = when (feedType) {
				FeedType.BOARD -> textFeedCursorId
				FeedType.BOARD_PICTURE -> imageFeedCursorId
			}
			_uiState.update {
				HomeUiState.Loading
			}
			viewModelScope.launch {
				getPopularBoardsUseCase(
					cursorId = cursorId,
					boardType = feedType.name,
					popularType = PopularType.WHISKY.name
				).catch { t ->
					_uiState.update { HomeUiState.Failure(t.message ?: "") }
				}.collect {
					when (feedType) {
						FeedType.BOARD -> {
							_textFeedList.compareAndSet(textFeedList.value, it)
							textFeedCursorId = if (it.isNotEmpty()) {
								it.last().feedId
							} else {
								textFeedCursorId
							}
						}

						FeedType.BOARD_PICTURE -> {
							_imageFeedList.compareAndSet(imageFeedList.value, it)
							imageFeedCursorId = if (it.isNotEmpty()) {
								it.last().feedId
							} else {
								imageFeedCursorId
							}
						}
					}
					_uiState.update { HomeUiState.Success }
				}
			}
		}
	}

	private fun resetData() {
		_textFeedList.compareAndSet(_textFeedList.value, emptyList())
		_imageFeedList.compareAndSet(_imageFeedList.value, emptyList())

		textFeedCursorId = 0
		imageFeedCursorId = 0
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
		)
	)
}