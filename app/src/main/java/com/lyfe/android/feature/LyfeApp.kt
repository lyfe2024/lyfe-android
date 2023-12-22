package com.lyfe.android.feature

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.lyfe.android.core.navigation.LyfeNavHost
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.common.ui.navigation.NavigationTab

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LyfeApp(
	navigator: LyfeNavigator
) {
	val density = LocalDensity.current
	val navController = rememberNavController()
	val scrollState = rememberScrollState()

	var isNavigationBarHide by remember { mutableStateOf(false) }
	var isNeedNavigationTabIndicatorAnimation by remember { mutableStateOf(false) }

	LaunchedEffect(scrollState) {
		snapshotFlow { scrollState.isScrollInProgress }
			.collect {
				isNavigationBarHide = it
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

	val bottomNavItems = listOf(
		BottomNavItem.Home,
		BottomNavItem.Feed,
		BottomNavItem.Post,
		BottomNavItem.Alarm,
		BottomNavItem.Profile
	)
	var selected by remember { mutableIntStateOf(0) }

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		LyfeNavHost(
			modifier = Modifier.fillMaxSize(),
			navHostController = navController,
			navigator = navigator,
			isScroll = {
				isNavigationBarHide = it
			}
		) { route ->
			val idx = bottomNavItems.indexOfFirst { item -> item.screenRoute == route }
			selected = idx
		}

		if (selected != -1) {
			AnimatedVisibility(
				modifier = Modifier.align(Alignment.BottomCenter),
				visible = !isNavigationBarHide,
				enter = slideInVertically {
					// Slide in from 40 dp from the top.
					with(density) { -40.dp.roundToPx() }
				} + expandVertically(
					// Expand from the top.
					expandFrom = Alignment.Top
				) + fadeIn(
					// Fade in with the initial alpha of 0.3f.
					initialAlpha = 0.3f
				),
				exit = slideOutVertically() + shrinkVertically() + fadeOut()
			) {
				NavigationTab(
					modifier = Modifier
						.fillMaxWidth()
						.padding(bottom = 8.dp, start = 20.dp, end = 20.dp),
					items = bottomNavItems,
					selectedItemIndex = selected,
					isNeedIndicatorAnimation = !isNavigationBarHide && !this.transition.isRunning,
					onClick = { index ->
						selected = index
						navigator.navigate(bottomNavItems[index].screenRoute)
					}
				)
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

	object Post : BottomNavItem(
		title = R.string.btm_nav_post,
		defaultIconRes = R.drawable.ic_btm_navi_post_default,
		selectedIconRes = R.drawable.ic_btm_navi_post_selected,
		description = "게시 아이콘",
		screenRoute = LyfeScreens.Post.name
	)

	object Alarm : BottomNavItem(
		title = R.string.btm_nav_alarm,
		defaultIconRes = R.drawable.ic_btm_navi_alarm_default,
		selectedIconRes = R.drawable.ic_btm_navi_alarm_selected,
		description = "알림 아이콘",
		screenRoute = LyfeScreens.Alarm.name
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