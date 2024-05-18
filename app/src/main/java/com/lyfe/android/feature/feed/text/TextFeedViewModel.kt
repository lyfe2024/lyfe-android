package com.lyfe.android.feature.feed.text

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetTextBoardsUseCase
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.feed.model.FeedSortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TextFeedViewModel @Inject constructor(
	getTextBoardsUseCase: GetTextBoardsUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<TextFeedListUiState>(TextFeedListUiState.Loading)
	val uiState = _uiState.asStateFlow()

	private val _feedSortType = MutableStateFlow(FeedSortType.LATEST)
	val feedSortType get() = _feedSortType.asStateFlow()

	private val prevFeedList = mutableListOf<Feed>()
	private val feedFetchingLastFeedId = MutableStateFlow(0L)
	val feedList: StateFlow<List<Feed>> = feedFetchingLastFeedId.flatMapLatest { lastFeedId ->
		getTextBoardsUseCase(
			cursorId = lastFeedId,
			sortType = feedSortType.value
		).onStart {
			_uiState.value = TextFeedListUiState.Loading
		}.onCompletion {
			_uiState.value = TextFeedListUiState.IDLE
		}.catch {
			_uiState.value = TextFeedListUiState.Error(it.message)
		}.map {
			prevFeedList.addAll(it)
			prevFeedList.toList()
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = emptyList()
	)

	fun fetchNextFeedList() {
		if (uiState.value != TextFeedListUiState.Loading) {
			feedFetchingLastFeedId.value = prevFeedList.last().feedId
		}
	}

	fun selectFeedSortType(feedSortType: FeedSortType) {
		if (_feedSortType.value != feedSortType) {
			_feedSortType.value = feedSortType
			prevFeedList.clear()
			feedFetchingLastFeedId.value = 0L
		}
	}
}