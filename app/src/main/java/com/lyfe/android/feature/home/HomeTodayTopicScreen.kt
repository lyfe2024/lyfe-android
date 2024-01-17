package com.lyfe.android.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.core.common.ui.component.LyfeCardViewDesignType
import com.lyfe.android.core.common.ui.component.LyfeFeedCardView
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Feed
import com.lyfe.android.core.model.FeedFetchingType
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun HomeTodayTopicScreen(
	viewModel: HomeViewModel = hiltViewModel(),
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit
) {
	// 백엔드랑 API 어떤식으로 처리할지 의논하고 로직 수정해야할 듯
	val imageFeeds by viewModel.imageFeedList.collectAsStateWithLifecycle()
	val textFeeds by viewModel.textFeedList.collectAsStateWithLifecycle()
	val scrollState = rememberLazyListState()

	LaunchedEffect(Unit) {
		viewModel.fetchImageFeedList()
		viewModel.fetchTextFeedList()
	}

	LaunchedEffect(scrollState) {
		snapshotFlow { scrollState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	LazyColumn(
		state = scrollState
	) {
		itemsIndexed(
			items = textFeeds,
			key = { _, feed ->
				feed.feedId
			}
		) { index, feed ->
			if (index == 0) {
				Text(
					modifier = Modifier.fillMaxWidth()
						.padding(horizontal = 20.dp),
					text = "여름과 가을 사이",
					style = TextStyle(
						fontSize = 28.sp,
						fontWeight = FontWeight.W700,
						lineHeight = 38.sp,
						color = Main500,
						fontFamily = pretenard
					),
					maxLines = 2
				)

				Spacer(modifier = Modifier.height(8.dp))

				HomeSwipeableFeeds(
					modifier = Modifier.padding(horizontal = 20.dp),
					feeds = imageFeeds.reversed().toMutableList(),
					onClick = { navigator.navigate(LyfeScreens.FeedDetail.name) }
				)

				Spacer(modifier = Modifier.height(24.dp))
			} else {
				if (index == 1) {
					HomeTodayTopicTextFeedTopBar(
						modifier = Modifier.padding(horizontal = 20.dp),
						fetchingType = viewModel.textFeedFetchingType,
						onFetchingTypeChanged = {
							viewModel.updateFeedFetchingType(it)
						}
					)
				} else if (index % 5 == 0) {
					HomeTodayTopicImageFeedList(
						modifier = Modifier.padding(vertical = 16.dp),
						feeds = imageFeeds,
						contentPadding = PaddingValues(
							horizontal = 20.dp,
							vertical = 16.dp
						)
					)
				}

				HomeTextFeedView(
					modifier = Modifier.padding(horizontal = 20.dp),
					feed = feed
				)

				Divider(
					modifier = Modifier.padding(horizontal = 20.dp),
					color = Grey100,
					thickness = 1.dp
				)
			}
		}
	}
}

@Composable
private fun HomeTodayTopicTextFeedTopBar(
	modifier: Modifier,
	fetchingType: FeedFetchingType,
	onFetchingTypeChanged: (FeedFetchingType) -> Unit
) {
	Row(
		modifier = modifier
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = "고민글",
			style = TextStyle(
				color = Color.Black,
				fontFamily = pretenard,
				fontSize = 20.sp,
				fontWeight = FontWeight.W700,
				lineHeight = 32.sp
			)
		)

		Spacer(modifier = Modifier.weight(1f))

		Text(
			modifier = Modifier.clickableSingle {
				onFetchingTypeChanged(FeedFetchingType.LATEST)
			},
			text = "최신순",
			style = TextStyle.getTextStyle(fetchingType == FeedFetchingType.LATEST)
		)

		Text(
			modifier = Modifier
				.fillMaxHeight()
				.padding(horizontal = 4.dp),
			text = "|",
			color = Grey200,
		)

		Text(
			modifier = Modifier.clickableSingle {
				onFetchingTypeChanged(FeedFetchingType.POPULAR)
			},
			text = "인기순",
			style = TextStyle.getTextStyle(fetchingType == FeedFetchingType.POPULAR)
		)
	}
}

@Composable
private fun HomeTodayTopicImageFeedList(
	modifier: Modifier,
	feeds: List<Feed>,
	contentPadding: PaddingValues,
) {
	Column(
		modifier = modifier.fillMaxWidth()
	) {
		Row(
			modifier = Modifier.padding(horizontal = 20.dp),
			verticalAlignment = Alignment.CenterVertically
		){
			Text(
				modifier = Modifier.weight(1f),
				text = "댓글이 많이 달린",
				color = Color.Black,
				fontSize = 20.sp,
				fontWeight = FontWeight.W700,
				fontFamily = pretenard
			)

			Text(
				text = "더보기",
				color = Color.Black,
				fontSize = 12.sp,
				fontWeight = FontWeight.W400,
				fontFamily = pretenard
			)
		}

		LazyRow(
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			contentPadding = contentPadding
		) {
			itemsIndexed(
				items = feeds,
				key = { _, feed -> feed.feedId}
			) { index, feed ->
				LyfeFeedCardView(
					modifier = Modifier,
					feed = feed,
					designType = LyfeCardViewDesignType.HOME_SCREEN_CARD
				)
			}
		}
	}
}

@Composable
private fun TextStyle.Companion.getTextStyle(isSelected: Boolean): TextStyle {
	return if (isSelected) {
		TextStyle(
			color = Main500,
			fontFamily = pretenard,
			fontSize = 14.sp,
			fontWeight = FontWeight.W700,
			lineHeight = 22.sp
		)
	} else {
		TextStyle(
			color = Grey200,
			fontFamily = pretenard,
			fontSize = 14.sp,
			fontWeight = FontWeight.W400,
			lineHeight = 22.sp
		)
	}
}