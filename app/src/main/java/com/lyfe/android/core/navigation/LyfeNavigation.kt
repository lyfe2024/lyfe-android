package com.lyfe.android.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.alarm.NotificationListRoute
import com.lyfe.android.feature.album.SelectAlbumScreen
import com.lyfe.android.feature.detail.FeedDetailRouter
import com.lyfe.android.feature.feed.FeedScreen
import com.lyfe.android.feature.feedback.FeedbackScreen
import com.lyfe.android.feature.home.HomeScreen
import com.lyfe.android.feature.login.LoginScreen
import com.lyfe.android.feature.nickname.CreateNicknameScreen
import com.lyfe.android.feature.post.PostScreen
import com.lyfe.android.feature.post.create.PostCreateScreen
import com.lyfe.android.feature.profile.ProfileScreen
import com.lyfe.android.feature.profileedit.ProfileEditScreen
import com.lyfe.android.feature.setting.SettingScreen
import com.lyfe.android.feature.signup.SignUpCompleteScreen
import com.lyfe.android.feature.signup.SignUpTermsPolicyScreen
import com.lyfe.android.feature.terms.PersonalInfoAgreementsScreen
import com.lyfe.android.feature.terms.ServiceTermsScreen

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
		FeedDetailRouter(
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

	composable(route = LyfeScreens.NotificationList.name) {
		NotificationListRoute()
		selectedScreen(LyfeScreens.NotificationList.name)
	}

	composable(route = LyfeScreens.Profile.name) {
		ProfileScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
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

	composable(route = LyfeScreens.Feedback.name) {
		FeedbackScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
		selectedScreen(LyfeScreens.Feedback.name)
	}

	composable(route = LyfeScreens.Login.name) {
		LoginScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
		selectedScreen(LyfeScreens.Login.name)
	}

	composable(route = LyfeScreens.CreateNickname.name) {
		CreateNicknameScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.CreateNickname.name)
	}

	composable(
		route = "${LyfeScreens.SignUpTerms.name}/{nickname}",
		arguments = listOf(
			navArgument("nickname") {
				type = NavType.StringType
				defaultValue = ""
			}
		)
	) {
		SignUpTermsPolicyScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
		selectedScreen(LyfeScreens.SignUpTerms.name)
	}

	composable(route = LyfeScreens.ServiceTerms.name) {
		ServiceTermsScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
		selectedScreen(LyfeScreens.ServiceTerms.name)
	}

	composable(route = LyfeScreens.PersonalInfoTermsScreen.name) {
		PersonalInfoAgreementsScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
		selectedScreen(LyfeScreens.PersonalInfoTermsScreen.name)
	}

	composable(route = LyfeScreens.SignUpComplete.name) {
		SignUpCompleteScreen(navigator = lyfeNavigator)
		selectedScreen(LyfeScreens.SignUpComplete.name)
	}
}