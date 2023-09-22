package com.lyfe.android.feature.post

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun PostScreen(
	navigator: LyfeNavigator
) {
	Column {
		Text(text = "PostScreen")

		Button(
			onClick = {
				navigator.navigate(LyfeScreens.Alarm.name)
			}
		) {
			Text(text = "Alarm Btn")
		}
	}
}