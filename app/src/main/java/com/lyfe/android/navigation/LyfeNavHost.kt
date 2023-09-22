package com.lyfe.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun LyfeNavHost(
	modifier: Modifier = Modifier,
	navHostController: NavHostController,
	navigator: LyfeNavigator
) {
	NavHost(
		modifier = modifier,
		navController = navHostController,
		startDestination = LyfeScreens.Home.route
	) {
		lyfeHomeNavigation(
			lyfeNavigator = navigator
		)
	}
}