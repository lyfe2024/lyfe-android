package com.lyfe.android.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetLatestBoardsUseCase
import com.lyfe.android.core.domain.usecase.GetPopularBoardsUseCase
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedType
import com.lyfe.android.feature.feed.model.FeedSortType
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class FeedViewModel @Inject constructor(
	getLatestBoardsUseCase: GetLatestBoardsUseCase,
	getPopularBoardsUseCase: GetPopularBoardsUseCase
) : ViewModel() {

	private val _latestFeedUiState = MutableStateFlow<LatestFeedListUiState>(LatestFeedListUiState.Loading)
	val latestFeedUiState = _latestFeedUiState.asStateFlow()

	private val _popularFeedUiState = MutableStateFlow<PopularFeedListUiState>(PopularFeedListUiState.Loading)
	val popularFeedUiState = _popularFeedUiState.asStateFlow()

	private val _feedSortType = MutableStateFlow(FeedSortType.WHISKY_DESC)
	val feedSortType get() = _feedSortType.asStateFlow()

	private val prevLatestFeedList = mutableListOf<Feed>()
	private val latestFeedFetchingLastFeedId = MutableStateFlow(0L)
	val latestFeedList: StateFlow<List<Feed>> = latestFeedFetchingLastFeedId.flatMapLatest { lastFeedId ->
		getLatestBoardsUseCase(
			cursorId = lastFeedId,
			boardType = FeedType.BOARD_PICTURE.name
		).onStart {
			_latestFeedUiState.value = LatestFeedListUiState.Loading
		}.onCompletion {
			_latestFeedUiState.value = LatestFeedListUiState.IDLE
		}.catch {
			_latestFeedUiState.value = LatestFeedListUiState.Error(it.message)
		}.map {
			prevLatestFeedList.addAll(it)
			prevLatestFeedList.toList()
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = emptyList()
	)

	private val prevPopularFeedList = mutableListOf<Feed>()
	private val popularFeedFetchingLastFeedId = MutableStateFlow(0L)
	val popularFeedList: StateFlow<List<Feed>> = popularFeedFetchingLastFeedId.flatMapLatest { lastFeedId ->
		getPopularBoardsUseCase(
			cursorId = lastFeedId,
			boardType = FeedType.BOARD_PICTURE.name,
			popularType = feedSortType.value.name
		).onStart {
			_popularFeedUiState.value = PopularFeedListUiState.Loading
		}.onCompletion {
			_popularFeedUiState.value = PopularFeedListUiState.IDLE
		}.catch {
			_popularFeedUiState.value = PopularFeedListUiState.Error(it.message)
		}.map {
			prevPopularFeedList.addAll(it)
			prevPopularFeedList.toList()
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = emptyList()
	)
	fun fetchNextLatestFeedList() {
		if (latestFeedUiState.value != LatestFeedListUiState.Loading) {
			latestFeedFetchingLastFeedId.value = prevLatestFeedList.last().feedId
		}
	}

	fun fetchNextPopularFeedList() {
		if (popularFeedUiState.value != PopularFeedListUiState.Loading) {
			popularFeedFetchingLastFeedId.value = prevPopularFeedList.last().feedId
		}
	}

	fun selectFeedSortType(feedSortType: FeedSortType) {
		if (_feedSortType.value != feedSortType) {
			_feedSortType.value = feedSortType
			prevPopularFeedList.clear()
			popularFeedFetchingLastFeedId.value = 0L
		}
	}
}