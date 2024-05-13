package com.lyfe.android.feature.feed.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.core.common.ui.component.LyfeTextFeedView
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.feed.FeedFilterView
import com.lyfe.android.feature.feed.SelectFilterListView

@Composable
fun TextFeedScreen(
	viewModel: TextFeedViewModel = hiltViewModel(),
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit = {}
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val feedSortType by viewModel.feedSortType.collectAsStateWithLifecycle()
	val feedList by viewModel.feedList.collectAsStateWithLifecycle()
	val lazyListState = rememberLazyListState()
	var selectingState by remember { mutableStateOf(false) }

	LaunchedEffect(lazyListState) {
		snapshotFlow { lazyListState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	Box {
		Column(modifier = Modifier.fillMaxSize()) {
			FeedFilterView(
				modifier = Modifier.padding(vertical = 13.dp, horizontal = 20.dp),
				feedSortType = feedSortType,
				onClick = { selectingState = !selectingState }
			)

			TextFeedListScreen(
				lazyListState = lazyListState,
				feedList = feedList,
				uiState = uiState,
				fetchNextFeedList = viewModel::fetchNextFeedList,
				onFeedClick = {
					selectingState = false
					onFeedClick()
				}
			)
		}

		if (selectingState) {
			SelectFilterListView(
				feedSortType = feedSortType,
				onSelectSortType = {
					selectingState = false
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
	Box(modifier = Modifier.fillMaxSize()) {
		if (uiState == TextFeedListUiState.Loading) {
			// Progress Bar
			LogUtil.d("TextFeedScreen", "TextFeedListUiState Loading")
		}

		val threshold = 10

		LazyColumn(
			state = lazyListState,
			contentPadding = PaddingValues(horizontal = 20.dp),
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