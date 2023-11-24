package com.lyfe.android.feature.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun FeedScreen(
	navigator: LyfeNavigator
) {
	Box(
		modifier = Modifier.fillMaxSize()
	) {
		Text(
			modifier = Modifier.align(Alignment.Center),
			text = "Feed"
		)

		Button(
			onClick = {
				navigator.navigate(LyfeScreens.Home.name)
			}
		) {}
	}
}