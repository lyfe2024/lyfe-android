package com.lyfe.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun LyfeNavHost(
	modifier: Modifier = Modifier,
	navHostController: NavHostController,
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit,
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit,
	selectedScreen: (route: String) -> Unit
) {
	NavHost(
		modifier = modifier,
		navController = navHostController,
		startDestination = LyfeScreens.Home.route
	) {
		lyfeHomeNavigation(
			lyfeNavigator = navigator,
			navHostController = navHostController,
			onScroll = onScroll,
			onShowSnackBar = onShowSnackBar,
			selectedScreen = selectedScreen
		)
	}
}