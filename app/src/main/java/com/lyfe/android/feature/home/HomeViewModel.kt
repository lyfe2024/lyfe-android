package com.lyfe.android.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedFetchingType
import com.lyfe.android.feature.home.model.HomeFeedType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

	var uiState by mutableStateOf<HomeUiState>(HomeUiState.TodayTopicSuccess)
		private set
	var homeFeedType by mutableStateOf(HomeFeedType.TODAY_TOPIC)
		private set
	var textFeedFetchingType by mutableStateOf(FeedFetchingType.LATEST)
		private set

	private val _imageFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val imageFeedList get() = _imageFeedList.asStateFlow()

	private val _textFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val textFeedList get() = _textFeedList.asStateFlow()

	fun updateUiState(uiState: HomeUiState) {
		this.uiState = uiState
	}

	fun changeFilterType() {
		homeFeedType = if (homeFeedType == HomeFeedType.TODAY_TOPIC) {
			HomeFeedType.PAST_BEST
		} else {
			HomeFeedType.TODAY_TOPIC
		}
	}

	fun updateFeedFetchingType(fetchingType: FeedFetchingType) {
		textFeedFetchingType = fetchingType
	}

	fun fetchImageFeedList() {
		_imageFeedList.compareAndSet(imageFeedList.value, fakeFeedList)
	}

	fun fetchTextFeedList() {
		_textFeedList.compareAndSet(textFeedList.value, fakeFeedList + fakeFeedList2)
	}

	private val fakeFeedList = listOf(
		Feed(
			feedId = 1L,
			title = "타이틀1\n타이틀2",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/270/358",
			date = "2021-01-01",
			userId = 2L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/32/32",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 3L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/270/359",
			date = "2021-01-01",
			userId = 4L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/32/32",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 5L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/270/360",
			date = "2021-01-01",
			userId = 6L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/32/32",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 7L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/270/361",
			date = "2021-01-01",
			userId = 8L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/32/32",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		)
	)

	private val fakeFeedList2 = listOf(
		Feed(
			feedId = 9L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 10L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 11L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 12L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 13L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 14L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 15L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 16L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 17L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 18L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 19L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 20L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		)
	)
}