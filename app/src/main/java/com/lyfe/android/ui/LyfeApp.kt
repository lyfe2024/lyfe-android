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
	@DrawableRes val icon: Int,
	val description: String,
	val screenRoute: String
) {
	object Home : BottomNavItem(
		title = R.string.btm_nav_home,
		icon = R.drawable.ic_home_white,
		description = "홈 아이콘",
		screenRoute = LyfeScreens.Home.name
	)

	object Feed : BottomNavItem(
		title = R.string.btm_nav_view_all,
		icon = R.drawable.ic_feed_white,
		description = "피드 아이콘",
		screenRoute = LyfeScreens.Feed.name
	)

	object Post : BottomNavItem(
		title = R.string.btm_nav_post,
		icon = R.drawable.ic_circle_plus_white,
		description = "게시 아이콘",
		screenRoute = LyfeScreens.Post.name
	)

	object Alarm : BottomNavItem(
		title = R.string.btm_nav_alarm,
		icon = R.drawable.ic_alarm_white,
		description = "알림 아이콘",
		screenRoute = LyfeScreens.Alarm.name
	)

	object Profile : BottomNavItem(
		title = R.string.btm_nav_profile,
		icon = R.drawable.ic_profile_white,
		description = "프로필 아이콘",
		screenRoute = LyfeScreens.Profile.name
	)
}