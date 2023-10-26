package com.lyfe.android.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun ProfileScreen(
	navigator: LyfeNavigator
) {
	Column {
		Text(text = "ProfileScreen")

		Button(
			onClick = {
				navigator.navigateUp()
			}
		) {
			Text(text = "Profile Btn")
		}

		Button(
			onClick = {
				navigator.navigate(LyfeScreens.ProfileEdit.name)
			}
		) {
			Text(text = "Profile Edit")
		}
	}
}