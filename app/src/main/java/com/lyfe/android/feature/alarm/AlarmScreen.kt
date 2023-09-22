package com.lyfe.android.feature.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun AlarmScreen(
	navigator: LyfeNavigator
) {
	Column {
		Text(text = "AlarmScreen")

		Button(
			onClick = {
				navigator.navigate(LyfeScreens.Post.name)
			}
		) {
			Text(text = "Post Btn")
		}
	}
}