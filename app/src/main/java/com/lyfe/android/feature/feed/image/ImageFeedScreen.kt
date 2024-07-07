package com.lyfe.android.feature.feed.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Caption3
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.feed.FeedFilterView
import com.lyfe.android.feature.feed.FeedScreenCardView
import com.lyfe.android.feature.feed.SelectFilterListView

@Composable
fun ImageFeedScreen(
	viewModel: ImageFeedViewModel = hiltViewModel(),
	lazyGridState: LazyGridState = rememberLazyGridState(),
	selectingState: Boolean,
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit = {},
	onToggleFilterView: (Boolean) -> Unit = {}
) {
	val uiState by viewModel.imageFeedUiState.collectAsStateWithLifecycle()
	val feedList by viewModel.imageFeedList.collectAsStateWithLifecycle()
	val feedSortType by viewModel.feedSortType.collectAsStateWithLifecycle()

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
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 20.dp, vertical = 13.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = stringResource(R.string.feed_image_screen_description),
					style = Caption3,
					color = Grey300
				)

				FeedFilterView(
					feedSortType = feedSortType,
					onClick = { onToggleFilterView(!selectingState) }
				)
			}

			ImageFeedListScreen(
				lazyGridState = lazyGridState,
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