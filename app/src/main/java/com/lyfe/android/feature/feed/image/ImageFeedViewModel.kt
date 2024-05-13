package com.lyfe.android.feature.feed.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetImageBoardsUseCase
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
class ImageFeedViewModel @Inject constructor(
	getImageBoardsUseCase: GetImageBoardsUseCase
) : ViewModel() {

	private val _imageFeedUiState = MutableStateFlow<ImageFeedListUiState>(ImageFeedListUiState.Loading)
	val imageFeedUiState = _imageFeedUiState.asStateFlow()

	private val _feedSortType = MutableStateFlow(FeedSortType.LATEST)
	val feedSortType get() = _feedSortType.asStateFlow()

	private val prevImageFeedList = mutableListOf<Feed>()
	private val imageFeedFetchingLastFeedId = MutableStateFlow(0L)
	val imageFeedList: StateFlow<List<Feed>> = imageFeedFetchingLastFeedId.flatMapLatest { lastFeedId ->
		getImageBoardsUseCase(
			cursorId = lastFeedId,
			sortType = feedSortType.value
		).onStart {
			_imageFeedUiState.value = ImageFeedListUiState.Loading
		}.onCompletion {
			_imageFeedUiState.value = ImageFeedListUiState.IDLE
		}.catch {
			_imageFeedUiState.value = ImageFeedListUiState.Error(it.message)
		}.map {
			prevImageFeedList.addAll(it)
			prevImageFeedList.toList()
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5000L),
		initialValue = emptyList()
	)

	fun fetchNextFeedList() {
		if (imageFeedUiState.value != ImageFeedListUiState.Loading) {
			imageFeedFetchingLastFeedId.value = prevImageFeedList.last().feedId
		}
	}

	fun selectFeedSortType(feedSortType: FeedSortType) {
		if (_feedSortType.value != feedSortType) {
			_feedSortType.value = feedSortType
			prevImageFeedList.clear()
			imageFeedFetchingLastFeedId.value = 0L
		}
	}
}