package com.lyfe.android.feature.profile.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.Title1
import com.lyfe.android.core.model.Feed
import com.lyfe.android.feature.profile.ProfileScreenTextFeedView

private const val EMPTY_SPACER_HEIGHT = 0.5f

@Composable
fun ProfileTextFeedScreen(
	viewModel: ProfileTextFeedViewModel = hiltViewModel(),
	onFeedClick: () -> Unit,
	onPostButtonClick: () -> Unit,
	onScroll: (Boolean) -> Unit = {}
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	val feeds by viewModel.feedList.collectAsStateWithLifecycle()
	val lazyListState = rememberLazyListState()

	LaunchedEffect(lazyListState) {
		snapshotFlow { lazyListState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	if (uiState is ProfileTextFeedUiState.IDLE && feeds.isEmpty()) {
		FeedListEmptyView(
			onPostButtonClick = onPostButtonClick
		)
	} else {
		TextFeedListView(
			uiState = uiState,
			feeds = feeds,
			lazyListState = lazyListState,
			fetchNextFeedList = viewModel::fetchNextFeedList,
			onFeedClick = onFeedClick
		)
	}
}

@Composable
private fun TextFeedListView(
	uiState: ProfileTextFeedUiState,
	feeds: List<Feed>,
	lazyListState: LazyListState,
	fetchNextFeedList: () -> Unit,
	onFeedClick: () -> Unit
) {
	val threshold = 10

	LazyColumn(
		state = lazyListState,
		contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		itemsIndexed(feeds) { index, feed ->
			if ((index + threshold) >= feeds.size && uiState != ProfileTextFeedUiState.Loading) {
				fetchNextFeedList()
			}

			key(feed.feedId) {
				ProfileScreenTextFeedView(
					modifier = Modifier,
					feed = feed,
					onClick = onFeedClick
				)

				Spacer(modifier = Modifier.height(12.dp))

				Divider(color = Grey100, thickness = 1.dp)
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
			text = stringResource(R.string.profile_text_feed_empty),
			textAlign = TextAlign.Center,
			style = Title1,
			color = Grey900
		)
		
		Spacer(modifier = Modifier.height(24.dp))

		LyfeButton(
			buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
			isClearIconShow = false,
			text = stringResource(R.string.profile_text_feed_post)
		) {
			onPostButtonClick()
		}

		Spacer(modifier = Modifier.weight(1f))
	}
}