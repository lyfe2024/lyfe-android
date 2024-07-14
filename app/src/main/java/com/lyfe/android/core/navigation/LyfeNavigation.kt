package com.lyfe.android.core.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.alarm.NotificationListRoute
import com.lyfe.android.feature.album.SelectAlbumRouter
import com.lyfe.android.feature.detail.FeedDetailRouter
import com.lyfe.android.feature.feed.FeedScreen
import com.lyfe.android.feature.feedback.FeedbackScreen
import com.lyfe.android.feature.home.HomeScreen
import com.lyfe.android.feature.login.LoginScreen
import com.lyfe.android.feature.nickname.CreateNicknameScreen
import com.lyfe.android.feature.post.create.photo.CreatePhotoPostRouter
import com.lyfe.android.feature.post.create.text.CreateTextPostRouter
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
	onScreenShow: (route: String) -> Unit
) {
	composable(route = LyfeScreens.Home.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.Home.name)
		}

		HomeScreen(
			navigator = lyfeNavigator,
			onScroll = onScroll
		)
	}

	composable(route = LyfeScreens.Feed.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.Feed.name)
		}

		FeedScreen(
			navigator = lyfeNavigator,
			onScroll = onScroll
		)
	}

	composable(
		route = "${LyfeScreens.FeedDetail.name}/{boardId}",
		arguments = listOf(
			navArgument("boardId") {
				type = NavType.LongType
				defaultValue = -1L
			}
		)
	) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.FeedDetail.name)
		}

		FeedDetailRouter(
			navigator = lyfeNavigator
		)
	}

	composable(route = LyfeScreens.CreatePhotoPost.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.CreatePhotoPost.name)
		}

		CreatePhotoPostRouter(
			navigator = lyfeNavigator,
			navHostController = navHostController
		)
	}

	composable(route = LyfeScreens.CreateTextPost.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.CreateTextPost.name)
		}

		CreateTextPostRouter(
			navigator = lyfeNavigator
		)
	}

	composable(route = LyfeScreens.NotificationList.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.NotificationList.name)
		}

		NotificationListRoute()
	}

	composable(route = LyfeScreens.Profile.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.Profile.name)
		}

		ProfileScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.SelectAlbum.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.SelectAlbum.name)
		}

		SelectAlbumRouter(
			navigator = lyfeNavigator
		)
	}

	composable(route = LyfeScreens.ProfileEdit.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.ProfileEdit.name)
		}

		ProfileEditScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.Setting.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.Setting.name)
		}

		SettingScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.Feedback.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.Feedback.name)
		}

		FeedbackScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.Login.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.Login.name)
		}

		LoginScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.CreateNickname.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.CreateNickname.name)
		}

		CreateNicknameScreen(navigator = lyfeNavigator)
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
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.SignUpTerms.name)
		}

		SignUpTermsPolicyScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.ServiceTerms.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.ServiceTerms.name)
		}

		ServiceTermsScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.PersonalInfoTermsScreen.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.PersonalInfoTermsScreen.name)
		}

		PersonalInfoAgreementsScreen(
			navigator = lyfeNavigator,
			onShowSnackBar = onShowSnackBar
		)
	}

	composable(route = LyfeScreens.SignUpComplete.name) {
		LaunchedEffect(Unit) {
			onScreenShow(LyfeScreens.SignUpComplete.name)
		}

		SignUpCompleteScreen(navigator = lyfeNavigator)
	}
}