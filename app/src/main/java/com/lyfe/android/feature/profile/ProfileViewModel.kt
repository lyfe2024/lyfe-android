package com.lyfe.android.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
	var uiState by mutableStateOf<ProfileUiState>(ProfileUiState.Loading)
		private set

	private val _userInfo = mutableStateOf(UserInfo(0, "Guest", ""))
	val userInfo get() = _userInfo.value

	private val _imageFeedList = MutableStateFlow<List<Feed>>(emptyList())
	val imageFeedList get() = _imageFeedList.asStateFlow()

	private val _textFeedList = MutableStateFlow<List<TextFeed>>(emptyList())
	val textFeedList get() = _textFeedList.asStateFlow()

	init {
		viewModelScope.launch {
			// 게스트 로그인
			uiState = ProfileUiState.GuestSuccess
		}
	}
	fun updateToUserMode() {
		uiState = ProfileUiState.UserSuccess
		_userInfo.value = UserInfo(1, "설정된닉네임123", "https://picsum.photos/100")
	}

	fun fetchImageFeedList() {
		_imageFeedList.compareAndSet(imageFeedList.value, imageFeedList.value + fakeImageFeedList)
	}

	fun fetchTextFeedList() {
		_textFeedList.compareAndSet(textFeedList.value, fakeTextFeedList)
	}

	private val fakeImageFeedList = listOf(
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

	private val fakeTextFeedList = listOf(
		TextFeed(
			feedId = 1L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 2L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 3L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 4L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 5L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 6L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 7L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 8L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 9L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		),
		TextFeed(
			feedId = 10L,
			title = "Feed Title",
			content = "여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다. 여기는 이제 고민글의 내용이 표시되는 부분입니다.",
			date = "2021-01-01",
			userId = 1L,
			userName = "홍길동",
			whiskyCount = 32,
			commentCount = 50,
			isLike = false
		)
	)
}