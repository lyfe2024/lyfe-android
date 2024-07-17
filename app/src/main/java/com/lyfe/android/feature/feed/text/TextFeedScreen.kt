package com.lyfe.android.feature.feed.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeTextFeedView
import com.lyfe.android.core.common.ui.theme.Caption3
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.feed.FeedFilterView
import com.lyfe.android.feature.feed.SelectFilterListView

@Composable
fun TextFeedScreen(
	viewModel: TextFeedViewModel = hiltViewModel(),
	lazyListState: LazyListState = rememberLazyListState(),
	selectingState: Boolean,
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit = {},
	onToggleFilterView: (Boolean) -> Unit = {}
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val feedSortType by viewModel.feedSortType.collectAsStateWithLifecycle()
	val feedList by viewModel.feedList.collectAsStateWithLifecycle()

	LaunchedEffect(lazyListState) {
		snapshotFlow { lazyListState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	Box {
		Column(
			modifier = Modifier.fillMaxSize()
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 20.dp, vertical = 13.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = stringResource(R.string.feed_text_screen_description),
					style = Caption3,
					color = Grey300
				)

				FeedFilterView(
					feedSortType = feedSortType,
					onClick = {
						onToggleFilterView(!selectingState)
					}
				)
			}

			TextFeedListScreen(
				lazyListState = lazyListState,
				feedList = feedList,
				uiState = uiState,
				fetchNextFeedList = viewModel::fetchNextFeedList,
				onFeedClick = {
					onToggleFilterView(false)
					onFeedClick()
				}
			)
		}

		if (selectingState) {
			SelectFilterListView(
				modifier = Modifier
					.align(Alignment.TopEnd)
					.padding(top = 40.dp, end = 20.dp),
				feedSortType = feedSortType,
				onSelectSortType = {
					onToggleFilterView(false)
					viewModel.selectFeedSortType(it)
				}
			)
		}
	}
}

@Composable
private fun TextFeedListScreen(
	lazyListState: LazyListState,
	feedList: List<Feed>,
	uiState: TextFeedListUiState,
	fetchNextFeedList: () -> Unit,
	onFeedClick: () -> Unit
) {
	Box(
		modifier = Modifier.fillMaxSize()
	) {
		if (uiState == TextFeedListUiState.Loading) {
			// Progress Bar
			LogUtil.d("TextFeedScreen", "TextFeedListUiState Loading")
		}

		val threshold = 10

		LazyColumn(
			state = lazyListState,
			contentPadding = PaddingValues(horizontal = 20.dp)
		) {
			itemsIndexed(feedList) { index, feed ->
				if ((index + threshold) >= feedList.size && uiState != TextFeedListUiState.Loading) {
					fetchNextFeedList()
				}

				key(feed.feedId) {
					LyfeTextFeedView(feed = feed) {
						onFeedClick()
					}

					Divider(
						color = Grey100,
						thickness = 1.dp
					)
				}
			}
		}
	}
}