package com.lyfe.android.feature.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.feed.model.FeedSortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {

	private val _feedList = MutableStateFlow<List<Feed>>(emptyList())
	val feedList get() = _feedList.asStateFlow()

	var feedSortType by mutableStateOf(FeedSortType.WHISKY_DESC)
		private set

	fun fetchFeedList() {
		_feedList.compareAndSet(feedList.value, fakeFeedList)
	}

	fun selectFeedSortType(feedSortType: FeedSortType) {
		this.feedSortType = feedSortType
	}

	private val fakeFeedList = listOf(
		Feed(
			feedId = 1L,
			title = "타이틀1\n타이틀2",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 2L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 3L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 4L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 5L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 6L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 7L,
			title = "타이틀1",
			content = "컨텐츠1",
			feedImageUrl = "https://picsum.photos/700/700",
			date = "2021-01-01",
			userId = 8L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
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