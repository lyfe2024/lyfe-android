package com.lyfe.android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.alarm.AlarmScreen
import com.lyfe.android.feature.home.HomeScreen
import com.lyfe.android.feature.post.PostScreen
import com.lyfe.android.feature.profile.ProfileScreen
import com.lyfe.android.feature.profile_edit.ProfileEditScreen
import com.lyfe.android.feature.profile_edit.ProfileEditViewModel

fun NavGraphBuilder.lyfeHomeNavigation(
	lyfeNavigator: LyfeNavigator
) {
	composable(route = LyfeScreens.Home.name) {
		HomeScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.Post.name) {
		PostScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.Alarm.name) {
		AlarmScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.Profile.name) {
		ProfileScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.ProfileEdit.name) {
		ProfileEditScreen(viewModel = ProfileEditViewModel())
	}
}