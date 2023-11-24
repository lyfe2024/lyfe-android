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
import com.lyfe.android.feature.post.create.PostCreateScreen
import com.lyfe.android.feature.profile.ProfileScreen
import com.lyfe.android.feature.profileedit.ProfileEditScreen
import com.lyfe.android.feature.profileedit.ProfileEditViewModel

fun NavGraphBuilder.lyfeHomeNavigation(
	lyfeNavigator: LyfeNavigator,
	navHostController: NavHostController,
	selectedScreen: (route: String) -> Unit
) {
	composable(route = LyfeScreens.Home.name) {
		HomeScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.Home.name)
	}

	composable(route = LyfeScreens.Feed.name) {
		FeedScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.Feed.name)
	}

	composable(route = LyfeScreens.Post.name) {
		PostScreen(
			navigator = lyfeNavigator,
		)
		selectedScreen(LyfeScreens.Post.name)
	}

	composable(route = LyfeScreens.PostCreate.name) {
		PostCreateScreen(
			navigator = lyfeNavigator,
			navHostController = navHostController
		)
		selectedScreen(LyfeScreens.PostCreate.name)
	}

	composable(route = LyfeScreens.Alarm.name) {
		AlarmScreen()
		selectedScreen(LyfeScreens.Alarm.name)
	}

	composable(route = LyfeScreens.Profile.name) {
		ProfileScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.Profile.name)
	}

	composable(route = LyfeScreens.SelectAlbum.name) {
		SelectAlbumScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.SelectAlbum.name)
	}

	composable(route = LyfeScreens.ProfileEdit.name) {
		ProfileEditScreen(viewModel = ProfileEditViewModel())
		selectedScreen(LyfeScreens.ProfileEdit.name)
	}
}