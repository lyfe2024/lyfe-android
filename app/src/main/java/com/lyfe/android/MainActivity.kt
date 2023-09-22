package com.lyfe.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.ui.LyfeApp
import com.lyfe.android.ui.theme.LyfeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	@Inject
	internal lateinit var lyfeNavigator: LyfeNavigator

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			LyfeTheme {
				LyfeApp(
					navigator = lyfeNavigator
				)
			}
		}
	}
}