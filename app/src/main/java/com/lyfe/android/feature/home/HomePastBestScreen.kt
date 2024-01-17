package com.lyfe.android.feature.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun HomePastBestScreen(
	viewModel: HomeViewModel = hiltViewModel(),
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit
) {

}