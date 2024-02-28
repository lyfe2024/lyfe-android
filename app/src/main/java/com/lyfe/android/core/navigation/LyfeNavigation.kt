package com.lyfe.android.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.alarm.AlarmScreen
import com.lyfe.android.feature.album.SelectAlbumScreen
import com.lyfe.android.feature.detail.FeedDetailScreen
import com.lyfe.android.feature.feed.FeedScreen
import com.lyfe.android.feature.home.HomeScreen
import com.lyfe.android.feature.login.LoginScreen
import com.lyfe.android.feature.nickname.NicknameScreen
import com.lyfe.android.feature.policy.LoginCompleteScreen
import com.lyfe.android.feature.policy.PolicyScreen
import com.lyfe.android.feature.post.PostScreen
import com.lyfe.android.feature.post.create.PostCreateScreen
import com.lyfe.android.feature.profile.ProfileScreen
import com.lyfe.android.feature.profileedit.ProfileEditScreen
import com.lyfe.android.feature.setting.SettingScreen
import com.lyfe.android.feature.userexperience.UserExperienceScreen

fun NavGraphBuilder.lyfeHomeNavigation(
	lyfeNavigator: LyfeNavigator,
	navHostController: NavHostController,
	onScroll: (Boolean) -> Unit,
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit,
	selectedScreen: (route: String) -> Unit
) {
	composable(route = LyfeScreens.Home.name) {
		HomeScreen(
			navigator = lyfeNavigator,
			onScroll = onScroll
		)
		selectedScreen(LyfeScreens.Home.name)
	}

	composable(route = LyfeScreens.Feed.name) {
		FeedScreen(
			navigator = lyfeNavigator,
			onScroll = onScroll
		)
		selectedScreen(LyfeScreens.Feed.name)
	}

	composable(route = LyfeScreens.FeedDetail.name) {
		FeedDetailScreen(
			navigator = lyfeNavigator
		)
		selectedScreen(LyfeScreens.FeedDetail.name)
	}

	composable(route = LyfeScreens.Post.name) {
		PostScreen(navigator = lyfeNavigator)
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
		ProfileEditScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
		selectedScreen(LyfeScreens.ProfileEdit.name)
	}

	composable(route = LyfeScreens.Setting.name) {
		SettingScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
		selectedScreen(LyfeScreens.Setting.name)
	}

	composable(route = LyfeScreens.UserExperience.name) {
		UserExperienceScreen()
		selectedScreen(LyfeScreens.UserExperience.name)
	}

	composable(route = LyfeScreens.Login.name) {
		LoginScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.Login.name)
	}

	composable(route = LyfeScreens.Nickname.name) {
		NicknameScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.Nickname.name)
	}

	composable(route = LyfeScreens.Policy.name) {
		PolicyScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.Policy.name)
	}

	composable(route = LyfeScreens.LoginComplete.name) {
		LoginCompleteScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.LoginComplete.name)
	}
}