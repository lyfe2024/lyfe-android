package com.lyfe.android.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun HomeScreen(
	navigator: LyfeNavigator
) {
	Column {
		Text(text = "HomeScreen")

		Button(
			onClick = {
				navigator.navigate(LyfeScreens.Profile.name)
			}
		) {
			Text(text = "Home Btn")
		}
	}
}