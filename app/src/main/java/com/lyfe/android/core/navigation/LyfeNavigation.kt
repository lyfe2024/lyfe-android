package com.lyfe.android.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.alarm.AlarmScreen
import com.lyfe.android.feature.album.SelectAlbumScreen
import com.lyfe.android.feature.feed.FeedScreen
import com.lyfe.android.feature.home.HomeScreen
import com.lyfe.android.feature.post.PostScreen
import com.lyfe.android.feature.profile.ProfileScreen
import com.lyfe.android.feature.profileedit.ProfileEditScreen
import com.lyfe.android.feature.profileedit.ProfileEditViewModel

fun NavGraphBuilder.lyfeHomeNavigation(
	lyfeNavigator: LyfeNavigator,
	navHostController: NavHostController
) {
	composable(route = LyfeScreens.Home.name) {
		HomeScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.Feed.name) {
		FeedScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.Post.name) {
		PostScreen(
			navigator = lyfeNavigator,
			navHostController = navHostController
		)
	}

	composable(route = LyfeScreens.Alarm.name) {
		AlarmScreen()
	}

	composable(route = LyfeScreens.Profile.name) {
		ProfileScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.SelectAlbum.name) {
		SelectAlbumScreen(navigator = lyfeNavigator)
	}

	composable(route = LyfeScreens.ProfileEdit.name) {
		ProfileEditScreen(viewModel = ProfileEditViewModel())
	}
}