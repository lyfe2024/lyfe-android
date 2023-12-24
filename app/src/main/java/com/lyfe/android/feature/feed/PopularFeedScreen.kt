package com.lyfe.android.feature.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.feature.feed.model.FeedSortType

@Composable
fun PopularFeedScreen(
	modifier: Modifier = Modifier,
	viewModel: FeedViewModel = viewModel(),
	onScroll: (Boolean) -> Unit = {}
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

	Column(
		modifier = modifier
	) {
		FeedFilterView(
			modifier = Modifier.padding(vertical = 13.dp, horizontal = 20.dp),
			feedSortType = viewModel.feedSortType,
			selectSortType = {
				viewModel.selectFeedSortType(it)
			}
		)

		LazyVerticalGrid(
			columns = GridCells.Fixed(2),
			state = lazyGridState,
			contentPadding = PaddingValues(horizontal = 20.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			horizontalArrangement = Arrangement.spacedBy(18.dp)
		) {
			items(feedList) { feed ->
				key(feed.feedId) {
					FeedScreenCardView(feed = feed)
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
			selectSortType(FeedSortType.COMMENT_DESC)
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
			style = TextStyle(
				fontSize = 14.sp,
				lineHeight = 22.sp,
				fontWeight = FontWeight.W700,
				color = Color.Black
			)
		)
	}
}