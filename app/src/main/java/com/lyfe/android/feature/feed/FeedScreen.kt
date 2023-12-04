package com.lyfe.android.feature.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun FeedScreen(
	navigator: LyfeNavigator,
) {
	val tabs = listOf("Home", "About")
	var tabIdx by rememberSaveable { mutableIntStateOf(0) }

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Row {
			Text(
				modifier = Modifier,
				text = "Feed"
			)

			Button(
				onClick = {
					navigator.navigate(LyfeScreens.Home.name)
				}
			) {}
		}

		ScrollableTabRow(
			selectedTabIndex = tabIdx
		) {
			tabs.forEachIndexed { index, title ->
				Tab(
					text = { Text(title) },
					selected = tabIdx == index,
					onClick = { tabIdx = index },
					icon = {
						when (index) {
							0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
							1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
						}
					}
				)
			}
		}

		when (tabIdx) {
			0 -> LatestFeedScreen()
			1 -> PopularFeedScreen()
		}
	}
}