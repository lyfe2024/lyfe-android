package com.lyfe.android.feature.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
	navigator: LyfeNavigator,
	viewModel: TestViewModel = hiltViewModel()
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

		Button(
			onClick = {
				navigator.navigate(LyfeScreens.Login.name)
			}
		) {
			Text(text = "Login Btn")
		}

		val context = LocalContext.current
		Button(
			onClick = {
				runBlocking {
					val isSuccess = viewModel.fetchTestData()
					if (isSuccess) {
						Toast.makeText(context, "API 테스트 성공", Toast.LENGTH_SHORT).show()
					} else {
						Toast.makeText(context, "API 테스트 실패", Toast.LENGTH_SHORT).show()
					}
				}
			}
		) {
			Text(text = "API 통신 테스트")
		}
	}
}