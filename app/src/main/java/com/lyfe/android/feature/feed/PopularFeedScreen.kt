package com.lyfe.android.feature.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.feed.model.FeedSortType

@Composable
fun PopularFeedScreen(
	viewModel: FeedViewModel = hiltViewModel(),
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit = {}
) {
	val uiState by viewModel.popularFeedUiState.collectAsStateWithLifecycle()
	val feedSortType by viewModel.feedSortType.collectAsStateWithLifecycle()
	val feedList by viewModel.popularFeedList.collectAsStateWithLifecycle()
	val lazyGridState = rememberLazyGridState()

	LaunchedEffect(lazyGridState) {
		snapshotFlow { lazyGridState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	Column(modifier = Modifier.fillMaxSize()) {
		FeedFilterView(
			modifier = Modifier.padding(vertical = 13.dp, horizontal = 20.dp),
			feedSortType = feedSortType,
			selectSortType = viewModel::selectFeedSortType
		)

		PopularFeedListScreen(
			lazyGridState = lazyGridState,
			feedList = feedList,
			uiState = uiState,
			fetchNextFeedList = viewModel::fetchNextPopularFeedList,
			onFeedClick = onFeedClick
		)
	}
}

@Composable
private fun PopularFeedListScreen(
	lazyGridState: LazyGridState,
	feedList: List<Feed>,
	uiState: PopularFeedListUiState,
	fetchNextFeedList: () -> Unit,
	onFeedClick: () -> Unit
) {
	Box(modifier = Modifier.fillMaxSize()) {
		if (uiState == PopularFeedListUiState.Loading) {
			// Progress Bar
			LogUtil.d("PopularFeedScreen", "PopularFeedListUiState Loading")
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
				if ((index + threshold) >= feedList.size && uiState != PopularFeedListUiState.Loading) {
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

@Composable
private fun FeedFilterView(
	modifier: Modifier = Modifier,
	feedSortType: FeedSortType,
	selectSortType: (FeedSortType) -> Unit
) {
	Row(
		modifier = modifier.clickableSingle {
			when (feedSortType) {
				FeedSortType.WHISKY_DESC -> {
					selectSortType(FeedSortType.COMMENT_DESC)
				}
				FeedSortType.COMMENT_DESC -> {
					selectSortType(FeedSortType.WHISKY_DESC)
				}
			}
		},
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_arrow_down_black),
			contentDescription = "arrow_down"
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = feedSortType.content,
			color = Color.Black,
			style = Title3
		)
	}
}