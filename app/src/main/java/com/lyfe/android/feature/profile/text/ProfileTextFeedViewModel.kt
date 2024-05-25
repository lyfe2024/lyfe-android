package com.lyfe.android.feature.profile.text

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.domain.usecase.GetUserBoardUseCase
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedType
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
class ProfileTextFeedViewModel @Inject constructor(
	getUserBoardUseCase: GetUserBoardUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow<ProfileTextFeedUiState>(ProfileTextFeedUiState.Loading)
	val uiState = _uiState.asStateFlow()

	private val prevFeedList = mutableListOf<Feed>()
	private val feedFetchingLastFeedId = MutableStateFlow(0L)
	val feedList: StateFlow<List<Feed>> = feedFetchingLastFeedId.flatMapLatest { lastFeedId ->
		getUserBoardUseCase(
			boardType = FeedType.BOARD.name,
			cursorId = lastFeedId
		).onStart {
			_uiState.value = ProfileTextFeedUiState.Loading
		}.onCompletion {
			_uiState.value = ProfileTextFeedUiState.IDLE
		}.catch {
			_uiState.value = ProfileTextFeedUiState.Error(it.message)
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
		if (uiState.value != ProfileTextFeedUiState.Loading) {
			feedFetchingLastFeedId.value = prevFeedList.last().feedId
		}
	}
}