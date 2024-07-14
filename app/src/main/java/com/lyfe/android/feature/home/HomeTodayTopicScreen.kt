package com.lyfe.android.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
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
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextFeedView
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.H4
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.Title2
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedType
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

private const val DIVIDER_ALPHA = 0.5f

@Composable
fun HomeTodayTopicScreen(
	viewModel: HomeViewModel = hiltViewModel(),
	navigator: LyfeNavigator
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
			navigator.navigate("${LyfeScreens.FeedDetail.name}/$it")
		},
		onMoreFeedClick = {
			navigator.navigate(LyfeScreens.Feed.name)
		}
	)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeTodayTopicFeedArea(
	todayTopic: String,
	imageFeeds: List<Feed>,
	textFeeds: List<Feed>,
	onFeedClick: (feedId: Long) -> Unit,
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
			modifier = Modifier.alpha(DIVIDER_ALPHA),
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
				textFeeds.size + 1
			},
			feeds = textFeeds,
			onFeedClick = onFeedClick,
			onMoreFeedClick = onMoreFeedClick
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
	onFeedClick: (feedId: Long) -> Unit,
	onMoreFeedClick: () -> Unit
) {
	HorizontalPager(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		state = pagerState,
		flingBehavior = PagerDefaults.flingBehavior(
			state = pagerState,
			pagerSnapDistance = PagerSnapDistance.atMost(0)
		),
		pageSpacing = 8.dp,
		contentPadding = PaddingValues(horizontal = 20.dp)
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.border(
					width = 1.dp,
					color = Grey100,
					shape = RoundedCornerShape(16.dp)
				)
		) {
			if (it == feeds.size) {
				MoreTextFeedView(
					onMoreFeedClick = onMoreFeedClick
				)
			} else {
				LyfeTextFeedView(
					modifier = Modifier
						.padding(
							horizontal = 12.dp,
							vertical = 16.dp
						),
					feed = feeds[it],
					onClick = {
						onFeedClick(feeds[it].feedId)
					}
				)
			}
		}
	}
}

@Composable
private fun MoreTextFeedView(
	onMoreFeedClick: () -> Unit
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 40.dp)
			.clickableSingle { onMoreFeedClick() }
	) {
		Column(
			modifier = Modifier
				.align(Alignment.Center),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = stringResource(R.string.home_text_feed_more),
				style = Title2,
				color = Color.Black
			)

			Spacer(modifier = Modifier.height(16.dp))

			LyfeButton(
				text = stringResource(id = R.string.home_feed_more),
				horizontalPadding = 24.dp,
				verticalPadding = 4.dp,
				cornerSize = 16.dp,
				isClearIconShow = false,
				onClick = onMoreFeedClick
			)
		}
	}
}