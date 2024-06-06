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

	companion object {
		private const val HOME_FEED_MAX_COUNT = 10
	}

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
						_textFeedList.compareAndSet(_textFeedList.value, it.subList(0, minOf(it.size, HOME_FEED_MAX_COUNT)))
						textFeedCursorId = if (it.isNotEmpty()) {
							it.last().feedId
						} else {
							textFeedCursorId
						}
					}
					FeedType.BOARD_PICTURE -> {
						_imageFeedList.compareAndSet(_imageFeedList.value, it.subList(0, minOf(it.size, HOME_FEED_MAX_COUNT)))
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
}