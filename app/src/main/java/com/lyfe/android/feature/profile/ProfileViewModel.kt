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

	private val _feedList = MutableStateFlow<List<Feed>>(emptyList())
	val feedList get() = _feedList.asStateFlow()

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

	fun fetchFeedList() {
		_feedList.compareAndSet(feedList.value, feedList.value + fakeFeedList)
	}

	private val fakeFeedList = listOf(
		Feed(
			feedId = 1L,
			title = "타이틀1",
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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
			content = "원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 대한민국의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.",
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