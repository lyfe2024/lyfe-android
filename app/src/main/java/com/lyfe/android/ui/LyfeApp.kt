package com.lyfe.android.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.lyfe.android.R
import com.lyfe.android.core.navigation.LyfeNavHost
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.ui.navigation.NavigationTab

@Composable
fun LyfeApp(
	navigator: LyfeNavigator
) {
	val navController = rememberNavController()

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
			navigator = navigator
		) { route ->
			val idx = bottomNavItems.indexOfFirst { item -> item.screenRoute == route }
			if (idx != -1) selected = idx
		}

		NavigationTab(
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 8.dp, start = 20.dp, end = 20.dp)
				.align(Alignment.BottomCenter),
			items = bottomNavItems,
			selectedItemIndex = selected,
			onClick = { index ->
				selected = index
				navigator.navigate(bottomNavItems[index].screenRoute)
			}
		)
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