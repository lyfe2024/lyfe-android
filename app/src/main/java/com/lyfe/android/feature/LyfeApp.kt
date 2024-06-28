package com.lyfe.android.feature

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeSnackBar
import com.lyfe.android.core.common.ui.component.LyfeSnackBarVisuals
import com.lyfe.android.core.common.ui.navigation.LyfeNavigationBar
import com.lyfe.android.core.navigation.LyfeNavHost
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun LyfeApp(
	modifier: Modifier = Modifier,
	navigator: LyfeNavigator
) {
	val density = LocalDensity.current
	val navController = rememberNavController()
	val scrollState = rememberScrollState()
	var snackBarVisuals by remember { mutableStateOf(LyfeSnackBarVisuals()) }
	val snackBarState = remember { SnackbarHostState() }

	var isNavigationBarHide by remember { mutableStateOf(false) }
	var isNeedNavigationTabIndicatorAnimation by remember { mutableStateOf(false) }

	val bottomNavItems = listOf(
		BottomNavItem.Home,
		BottomNavItem.Feed,
		BottomNavItem.CreatePost,
		BottomNavItem.Alarm,
		BottomNavItem.Profile
	)
	var prevbtmSelectedIdx by remember { mutableIntStateOf(0) }
	var btmSelectedIdx by remember { mutableIntStateOf(0) }
	var showBtmNavi by remember { mutableStateOf(true) }
	var createPostButtonsShow by remember { mutableStateOf(false) }

	LaunchedEffect(scrollState) {
		snapshotFlow { scrollState.isScrollInProgress }
			.collect {
				isNavigationBarHide = it
			}
	}

	LaunchedEffect(snackBarVisuals) {
		if (snackBarVisuals.message.isNotEmpty()) {
			snackBarState.showSnackbar(snackBarVisuals)
			snackBarVisuals = LyfeSnackBarVisuals()
		}
	}

	LaunchedEffect(isNavigationBarHide) {
		snapshotFlow { isNavigationBarHide }
			.collect {
				isNeedNavigationTabIndicatorAnimation = it
			}
	}

	LaunchedEffect(Unit) {
		navigator.handleNavigationCommands(navController)
	}

	Box(
		modifier = modifier
	) {
		LyfeNavHost(
			modifier = Modifier.fillMaxSize(),
			navHostController = navController,
			navigator = navigator,
			onScroll = {
				isNavigationBarHide = it
			},
			onShowSnackBar = { iconType, message ->
				snackBarVisuals = LyfeSnackBarVisuals(
					iconType = iconType,
					message = message
				)
			}
		) { route ->
			// 비어있는 경로는 Bottom Post Icon
			btmSelectedIdx = bottomNavItems.indexOfFirst { it.screenRoute == route }
			showBtmNavi = btmSelectedIdx != -1
		}

		if (showBtmNavi) {
			LyfeNavigationBar(
				isNavigationBarHide = isNavigationBarHide,
				bottomNavItems = bottomNavItems,
				selectedItemIndex = btmSelectedIdx,
				createPostButtonsShow = createPostButtonsShow,
				createPostButtonsDismiss = {
					createPostButtonsShow = false
					btmSelectedIdx = prevbtmSelectedIdx
					prevbtmSelectedIdx = -1
				},
				onClickPhotoBox = {
					createPostButtonsShow = false
					navigator.navigate(LyfeScreens.CreatePhotoPost.name)
				},
				onClickTextBox = {
					createPostButtonsShow = false
					navigator.navigate(LyfeScreens.CreateTextPost.name)
				},
				bottomItemClick = { index ->
					if (btmSelectedIdx != index) {
						prevbtmSelectedIdx = btmSelectedIdx
						btmSelectedIdx = index

						createPostButtonsShow = bottomNavItems[index] == BottomNavItem.CreatePost
						if (bottomNavItems[index] != BottomNavItem.CreatePost) {
							navigator.navigate(bottomNavItems[index].screenRoute)
						}
					}
				}
			)
		}

		SnackbarHost(
			modifier = Modifier.align(Alignment.BottomCenter),
			hostState = snackBarState
		) { snackBarData ->
			val visuals = snackBarData.visuals as LyfeSnackBarVisuals
			Column(
				modifier = Modifier.padding(horizontal = 24.dp)
			) {
				LyfeSnackBar(
					iconType = visuals.iconType,
					message = visuals.message
				)

				Spacer(modifier = Modifier.height(80.dp))
			}
		}
	}
}

sealed class BottomNavItem(
	@StringRes val title: Int,
	@DrawableRes val defaultIconRes: Int,
	@DrawableRes val selectedIconRes: Int,
	val description: String,
	val screenRoute: String
) {
	object Home : BottomNavItem(
		title = R.string.btm_nav_home,
		defaultIconRes = R.drawable.ic_btm_navi_home_default,
		selectedIconRes = R.drawable.ic_btm_navi_home_selected,
		description = "홈 아이콘",
		screenRoute = LyfeScreens.Home.name
	)

	object Feed : BottomNavItem(
		title = R.string.btm_nav_view_all,
		defaultIconRes = R.drawable.ic_btm_navi_feed_default,
		selectedIconRes = R.drawable.ic_btm_navi_feed_selected,
		description = "피드 아이콘",
		screenRoute = LyfeScreens.Feed.name
	)

	object CreatePost : BottomNavItem(
		title = R.string.btm_nav_post,
		defaultIconRes = R.drawable.ic_btm_navi_post_default,
		selectedIconRes = R.drawable.ic_btm_navi_post_selected,
		description = "게시 아이콘",
		screenRoute = ""
	)

	object Alarm : BottomNavItem(
		title = R.string.btm_nav_alarm,
		defaultIconRes = R.drawable.ic_btm_navi_alarm_default,
		selectedIconRes = R.drawable.ic_btm_navi_alarm_selected,
		description = "알림 아이콘",
		screenRoute = LyfeScreens.NotificationList.name
	)

	object Profile : BottomNavItem(
		title = R.string.btm_nav_profile,
		defaultIconRes = R.drawable.ic_btm_navi_profile_default,
		selectedIconRes = R.drawable.ic_btm_navi_profile_selected,
		description = "프로필 아이콘",
		screenRoute = LyfeScreens.Profile.name
	)

	fun getIcon(isSelected: Boolean) = if (isSelected) {
		selectedIconRes
	} else {
		defaultIconRes
	}
}