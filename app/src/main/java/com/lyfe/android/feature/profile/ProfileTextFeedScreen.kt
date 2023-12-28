package com.lyfe.android.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.core.common.ui.theme.Grey100

@Composable
fun ProfileTextFeedScreen(
	viewModel: ProfileViewModel
) {
	val textFeeds by viewModel.textFeedList.collectAsStateWithLifecycle()
	val lazyListState = rememberLazyListState()

	LaunchedEffect(Unit) {
		viewModel.fetchTextFeedList()
	}

	LazyColumn(
		state = lazyListState,
		contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(textFeeds) { textFeed ->
			key(textFeed.feedId) {
				ProfileScreenTextFeedView(
					modifier = Modifier,
					feed = textFeed
				)

				Spacer(modifier = Modifier.height(12.dp))

				Divider(color = Grey100, thickness = 1.dp)
			}
		}
	}
}