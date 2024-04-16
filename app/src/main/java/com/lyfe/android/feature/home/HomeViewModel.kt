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
import kotlinx.coroutines.flow.FlowCollector
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
	var textFeedFetchingType by mutableStateOf(FeedFetchingType.LATEST)
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
		textFeedFetchingType = fetchingType
	}

	fun fetchImageFeedList() {
		_imageFeedList.compareAndSet(imageFeedList.value, fakeFeedList)
	}

	fun fetchTextFeedList() = viewModelScope.launch {
		val onEach: (List<Feed>) -> Unit = {
			_uiState.update { HomeUiState.Loading }
		}
		val catcher: FlowCollector<List<Feed>>.(Throwable) -> Unit = { t ->
			_uiState.update { HomeUiState.Failure(t.message ?: "") }
		}
		val feedCollector: (List<Feed>) -> Unit = {
			_textFeedList.compareAndSet(textFeedList.value, it)
			_uiState.update { HomeUiState.TodayTopicSuccess }
		}
		when (textFeedFetchingType) {
			FeedFetchingType.POPULAR -> {
				getPopularBoardsUseCase(
					cursorId = 0,
					boardType = FeedType.BOARD.name,
					popularType = PopularType.WHISKY.name
				).onEach(onEach)
				.catch(catcher)
				.collect(feedCollector)
			}
			FeedFetchingType.LATEST -> {
				getLatestBoardsUseCase(
					cursorId = 0,
					boardType = FeedType.BOARD.name
				).onEach(onEach)
				.catch(catcher)
				.collect(feedCollector)
			}
		}
	}

	private val fakeFeedList = listOf(
		Feed(
			feedId = 1L,
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
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
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
			date = "2021-01-01",
			userId = 20L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 21L,
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
			date = "2021-01-01",
			userId = 20L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 23L,
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
			date = "2021-01-01",
			userId = 20L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 25L,
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
			date = "2021-01-01",
			userId = 20L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 27L,
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
			date = "2021-01-01",
			userId = 20L,
			userName = "홍길동",
			userProfileImgUrl = "https://picsum.photos/700/700",
			whiskyCount = 1,
			commentCount = 1,
			isLike = false
		),
		Feed(
			feedId = 29L,
			title = "사진 제목 텍스트\n" + "두줄까지 들어가고 넘어가는건 어떠떨까떬떨세셍",
			content = "여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지. 여기는 내용 들어옵니다. 최대 2줄까지",
			feedImageUrl = "",
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