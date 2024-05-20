package com.lyfe.android.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeTextFeedView
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.H4
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedType
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun HomeTodayTopicScreen(
	viewModel: HomeViewModel = hiltViewModel(),
	navigator: LyfeNavigator,
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	val textFeeds by viewModel.textFeedList.collectAsStateWithLifecycle()
	val imageFeeds by viewModel.imageFeedList.collectAsStateWithLifecycle()

	LaunchedEffect(Unit) {
		viewModel.fetchLatestFeedList(FeedType.BOARD)
		viewModel.fetchLatestFeedList(FeedType.BOARD_PICTURE)
	}

	when (uiState) {
		HomeUiState.Success -> {
			LogUtil.i("UiState", "Home Success!!!")
		}
		HomeUiState.Loading -> {
			LogUtil.i("UiState", "Home Loading...")
		}
		is HomeUiState.Failure -> {
			val message = (uiState as HomeUiState.Failure).errorMessage
			LogUtil.e("UiState", "Home Failed: $message")
		}
	}

	HomeTodayTopicFeedArea(
		todayTopic = viewModel.todayTopic,
		imageFeeds = imageFeeds,
		textFeeds = textFeeds,
		onFeedClick = {
			navigator.navigate(LyfeScreens.FeedDetail.name)
		},
		onMoreFeedClick = {
			navigator.navigate(LyfeScreens.Feed.name)
		},
	)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeTodayTopicFeedArea(
	todayTopic: String,
	imageFeeds: List<Feed>,
	textFeeds: List<Feed>,
	onFeedClick: (Feed) -> Unit,
	onMoreFeedClick: () -> Unit
) {
	Column {
		HomeTopicText(todayTopic)

		Spacer(modifier = Modifier.height(8.dp))

		HomeSwipeableImageFeeds(
			modifier = Modifier.padding(horizontal = 20.dp),
			feeds = imageFeeds,
			onFeedClick = onFeedClick,
			onMoreFeedClick = onMoreFeedClick
		)

		Spacer(modifier = Modifier.height(32.dp))

		Divider(
			modifier = Modifier.alpha(0.5f),
			color = Grey100,
			thickness = 8.dp
		)

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			modifier = Modifier.padding(horizontal = 20.dp),
			text = stringResource(R.string.home_text_feed),
			color = Color.Black,
			style = H4
		)

		Spacer(modifier = Modifier.height(8.dp))

		HomeTextFeedPager(
			pagerState = rememberPagerState {
				textFeeds.size
			},
			feeds = textFeeds,
			onFeedClick = onFeedClick
		)
	}
}

@Composable
private fun HomeTopicText(text: String) {
	Text(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp),
		text = text,
		style = TextStyle(
			fontSize = 28.sp,
			fontWeight = FontWeight.W700,
			lineHeight = 38.sp,
			color = Main500,
			fontFamily = pretenard
		),
		maxLines = 2
	)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeTextFeedPager(
	pagerState: PagerState,
	feeds: List<Feed>,
	onFeedClick: (Feed) -> Unit
) {
	HorizontalPager(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		state = pagerState
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 20.dp)
				.border(
					width = 1.dp,
					color = Grey100,
					shape = RoundedCornerShape(16.dp)
				)
		) {
			LyfeTextFeedView(
				modifier = Modifier
					.padding(
						horizontal = 12.dp,
						vertical = 16.dp
					)
					.clickableSingle { onFeedClick(feeds[it]) },
				feed = feeds[it]
			)
		}
	}
}