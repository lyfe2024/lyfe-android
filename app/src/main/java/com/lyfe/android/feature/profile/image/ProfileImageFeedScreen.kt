package com.lyfe.android.feature.profile.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.Title1
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.profile.ProfileScreenImageFeedView

private const val EMPTY_SPACER_HEIGHT = 0.5f

@Composable
fun ProfileImageFeedScreen(
	viewModel: ProfileImageFeedViewModel = hiltViewModel(),
	onFeedClick: (feedId: Long) -> Unit,
	onPostButtonClick: () -> Unit,
	onScroll: (Boolean) -> Unit = {}
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val feeds by viewModel.feedList.collectAsStateWithLifecycle()
	val lazyGridState = rememberLazyGridState()

	LaunchedEffect(lazyGridState) {
		snapshotFlow { lazyGridState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	if (uiState is ProfileImageFeedUiState.IDLE && feeds.isEmpty()) {
		FeedListEmptyView(
			onPostButtonClick = onPostButtonClick
		)
	} else {
		ImageFeedListView(
			uiState = uiState,
			feeds = feeds,
			lazyGridState = lazyGridState,
			fetchNextFeedList = viewModel::fetchNextFeedList,
			onFeedClick = onFeedClick
		)
	}
}

@Composable
private fun ImageFeedListView(
	uiState: ProfileImageFeedUiState,
	feeds: List<Feed>,
	lazyGridState: LazyGridState,
	fetchNextFeedList: () -> Unit,
	onFeedClick: (feedId: Long) -> Unit
) {
	val threshold = 10

	LazyVerticalGrid(
		state = lazyGridState,
		columns = GridCells.Fixed(2),
		contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		itemsIndexed(feeds) { index, feed ->
			if ((index + threshold) >= feeds.size && uiState != ProfileImageFeedUiState.Loading) {
				fetchNextFeedList()
			}

			key(feed.feedId) {
				ProfileScreenImageFeedView(
					modifier = Modifier,
					feed = feed,
					onClick = { onFeedClick(feed.feedId) }
				)
			}
		}
	}
}

@Composable
private fun FeedListEmptyView(
	onPostButtonClick: () -> Unit = {}
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Spacer(modifier = Modifier.weight(EMPTY_SPACER_HEIGHT))

		Text(
			text = stringResource(R.string.profile_image_feed_empty),
			textAlign = TextAlign.Center,
			style = Title1,
			color = Grey900
		)

		Spacer(modifier = Modifier.height(24.dp))

		LyfeButton(
			buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
			isClearIconShow = false,
			text = stringResource(R.string.profile_image_feed_post)
		) {
			onPostButtonClick()
		}

		Spacer(modifier = Modifier.weight(1f))
	}
}