package com.lyfe.android.feature.feed.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.feed.FeedFilterView
import com.lyfe.android.feature.feed.FeedScreenCardView
import com.lyfe.android.feature.feed.SelectFilterListView

@Composable
fun ImageFeedScreen(
	viewModel: ImageFeedViewModel = hiltViewModel(),
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit = {}
) {
	val uiState by viewModel.imageFeedUiState.collectAsStateWithLifecycle()
	val feedList by viewModel.imageFeedList.collectAsStateWithLifecycle()
	val feedSortType by viewModel.feedSortType.collectAsStateWithLifecycle()
	val lazyGridState = rememberLazyGridState()
	var selectingState by remember { mutableStateOf(false) }

	LaunchedEffect(lazyGridState) {
		snapshotFlow { lazyGridState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	Box {
		Column(
			modifier = Modifier.fillMaxSize()
		) {
			FeedFilterView(
				modifier = Modifier.padding(vertical = 13.dp, horizontal = 20.dp),
				feedSortType = feedSortType,
				onClick = { selectingState = !selectingState }
			)

			ImageFeedListScreen(
				lazyGridState = lazyGridState,
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
private fun ImageFeedListScreen(
	lazyGridState: LazyGridState,
	feedList: List<Feed>,
	uiState: ImageFeedListUiState,
	fetchNextFeedList: () -> Unit,
	onFeedClick: () -> Unit
) {
	Box(
		modifier = Modifier.fillMaxSize()
	) {
		if (uiState == ImageFeedListUiState.Loading) {
			// Progress Bar
			LogUtil.d("ImageFeedScreen", "ImageFeedListUiState Loading")
		}

		val threshold = 10

		LazyVerticalGrid(
			columns = GridCells.Fixed(2),
			state = lazyGridState,
			contentPadding = PaddingValues(horizontal = 20.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			horizontalArrangement = Arrangement.spacedBy(18.dp)
		) {
			itemsIndexed(feedList) { index, feed ->
				if ((index + threshold) >= feedList.size && uiState != ImageFeedListUiState.Loading) {
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
private fun Preview_ImageFeedScreen() {
	ImageFeedScreen()
}