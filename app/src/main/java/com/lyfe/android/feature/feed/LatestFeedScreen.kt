package com.lyfe.android.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.model.Feed

@Composable
fun LatestFeedScreen(
	viewModel: FeedViewModel = hiltViewModel(),
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit = {}
) {
	val uiState by viewModel.latestFeedUiState.collectAsStateWithLifecycle()
	val feedList by viewModel.latestFeedList.collectAsStateWithLifecycle()
	val lazyGridState = rememberLazyGridState()

	LaunchedEffect(lazyGridState) {
		snapshotFlow { lazyGridState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	LatestFeedListScreen(
		lazyGridState = lazyGridState,
		feedList = feedList,
		uiState = uiState,
		fetchNextFeedList = viewModel::fetchNextLatestFeedList,
		onFeedClick = onFeedClick
	)
}

@Composable
private fun LatestFeedListScreen(
	lazyGridState: LazyGridState,
	feedList: List<Feed>,
	uiState: LatestFeedListUiState,
	fetchNextFeedList: () -> Unit,
	onFeedClick: () -> Unit
) {
	Box(
		modifier = Modifier.fillMaxSize()
	) {
		if (uiState == LatestFeedListUiState.Loading) {
			// Progress Bar
			LogUtil.d("LatestFeedScreen", "LatestFeedListUiState Loading")
		}

		val threshold = 10

		LazyVerticalGrid(
			columns = GridCells.Fixed(2),
			state = lazyGridState,
			contentPadding = PaddingValues(vertical = 13.dp, horizontal = 20.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			horizontalArrangement = Arrangement.spacedBy(18.dp)
		) {
			itemsIndexed(feedList) { index, feed ->
				if ((index + threshold) >= feedList.size && uiState != LatestFeedListUiState.Loading) {
					fetchNextFeedList()
				}

				key(feed.feedId) {
					FeedScreenCardView(feed = feed) {
						onFeedClick()
					}
				}
			}
		}
	}
}

@Preview
@Composable
private fun Preview_LatestFeedScreen() {
	LatestFeedScreen()
}