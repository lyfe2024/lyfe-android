package com.lyfe.android.feature.home

import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val getTodayTopicUseCase: GetTodayTopicUseCase,
	private val getLatestBoardsUseCase: GetLatestBoardsUseCase,
	private val getPopularBoardsUseCase: GetPopularBoardsUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
	val uiState get() = _uiState.asStateFlow()

	var homeFeedType by mutableStateOf(HomeFeedType.TODAY_TOPIC)
		private set
	var feedFetchingType by mutableStateOf(FeedFetchingType.LATEST)
		private set

	private val _imageFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val imageFeedList get() = _imageFeedList.asStateFlow()

	private val _textFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val textFeedList get() = _textFeedList.asStateFlow()

	var todayTopic by mutableStateOf("오늘의 주제")
		private set

	init {
		getTodayTopic()
	}

	private fun getTodayTopic() = viewModelScope.launch {
		when (val result = getTodayTopicUseCase()) {
			is Result.Success -> {
				todayTopic = result.body?.content.orEmpty()
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
	}

	fun updateFeedFetchingType(fetchingType: FeedFetchingType) {
		if (feedFetchingType == fetchingType) {
			return
		}
		feedFetchingType = fetchingType
	}

	fun fetchLatestFeedList(
		feedType: FeedType
	)  {
		_uiState.update {
			HomeUiState.Loading
		}
		viewModelScope.launch {
			getLatestBoardsUseCase(
				cursorId = 0,
				boardType = feedType.name
			).catch { t ->
				_uiState.update { HomeUiState.Failure(t.message ?: "") }
			}.collect {
				when (feedType) {
					FeedType.BOARD -> {
						_textFeedList.compareAndSet(_textFeedList.value, it)
					}
					FeedType.BOARD_PICTURE -> {
						_imageFeedList.compareAndSet(_imageFeedList.value, it)
					}
				}
			}
		}
	}

	fun fetchPopularFeedList(
		feedType: FeedType
	)  {
		_uiState.update {
			HomeUiState.Loading
		}
		viewModelScope.launch {
			getPopularBoardsUseCase(
				cursorId = 0,
				boardType = feedType.name,
				popularType = PopularType.WHISKY.name
			).catch { t ->
				_uiState.update { HomeUiState.Failure(t.message ?: "") }
			}.collect {
				when (feedType) {
					FeedType.BOARD -> {
						_textFeedList.compareAndSet(textFeedList.value, it)
					}
					FeedType.BOARD_PICTURE -> {
						_imageFeedList.compareAndSet(imageFeedList.value, it)
					}
				}
			}
		}
	}
}