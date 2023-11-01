package com.lyfe.android.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.alarm.AlarmScreen
import com.lyfe.android.feature.home.HomeScreen
import com.lyfe.android.feature.post.PostScreen
import com.lyfe.android.feature.profile.ProfileScreen

fun NavGraphBuilder.lyfeHomeNavigation(
	lyfeNavigator: LyfeNavigator,
	navHostController: NavHostController,
) {
	composable(route = LyfeScreens.Home.name) {
		HomeScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.Post.name) {
		PostScreen(
			navigator = lyfeNavigator,
			navHostController = navHostController,
		)
	}

	composable(route = LyfeScreens.Alarm.name) {
		AlarmScreen()
	}

	composable(route = LyfeScreens.Profile.name) {
		ProfileScreen(navigator = lyfeNavigator)
	}
}