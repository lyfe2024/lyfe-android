package com.lyfe.android.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LatestFeedScreen(
	modifier: Modifier = Modifier,
	viewModel: FeedViewModel = viewModel(),
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit = {}
) {
	val feedList by viewModel.feedList.collectAsStateWithLifecycle()
	val lazyGridState = rememberLazyGridState()

	LaunchedEffect(Unit) {
		viewModel.fetchFeedList()
	}

	LaunchedEffect(lazyGridState) {
		snapshotFlow { lazyGridState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	Box(
		modifier = modifier
	) {
		LazyVerticalGrid(
			columns = GridCells.Fixed(2),
			state = lazyGridState,
			contentPadding = PaddingValues(vertical = 13.dp, horizontal = 20.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			horizontalArrangement = Arrangement.spacedBy(18.dp)
		) {
			items(feedList) { feed ->
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