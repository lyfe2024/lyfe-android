package com.lyfe.android.feature.feed

import androidx.lifecycle.ViewModel
import com.lyfe.android.core.model.Feed
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {

	val fakeFeedList = listOf(
		Feed(
			feedId = 1L,
			title = "타이틀1",
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