package com.lyfe.android.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileImageFeedScreen(
	viewModel: ProfileViewModel,
	onFeedClick: () -> Unit
) {
	val feeds by viewModel.feedList.collectAsStateWithLifecycle()
	val lazyGridState = rememberLazyGridState()

	LaunchedEffect(Unit) {
		viewModel.fetchFeedList()
	}

	LazyVerticalGrid(
		state = lazyGridState,
		columns = GridCells.Fixed(2),
		contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(feeds) { imageFeed ->
			key(imageFeed.feedId) {
				ProfileScreenImageFeedView(
					modifier = Modifier,
					feed = imageFeed,
					onClick = onFeedClick
				)
			}
		}
	}
}