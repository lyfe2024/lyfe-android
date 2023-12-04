package com.lyfe.android.feature.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PopularFeedScreen(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
	) {
		Text(
			modifier = Modifier.align(Alignment.Center),
			text = "PopularFeedScreen"
		)
	}
}