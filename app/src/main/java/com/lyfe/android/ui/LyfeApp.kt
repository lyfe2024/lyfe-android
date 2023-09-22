package com.lyfe.android.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lyfe.android.R
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.navigation.LyfeNavHost

@Composable
fun LyfeApp(
	navigator: LyfeNavigator
) {
	val navController = rememberNavController()

	LaunchedEffect(Unit) {
		navigator.handleNavigationCommands(navController)
	}

	val bottomNavItems = listOf(BottomNavItem.Home, BottomNavItem.Post, BottomNavItem.Alarm, BottomNavItem.Profile)

	Scaffold(
		bottomBar = {
			BottomNavigation(
				backgroundColor = Color.White
			) {
				val navBackStackEntry by navController.currentBackStackEntryAsState()
				val currentDestination = navBackStackEntry?.destination

				bottomNavItems.forEach { item ->
					BottomNavigationItem(
						icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.description) },
						label = { Text(text = stringResource(id = item.title)) },
						selectedContentColor = Color(color = 0xFF000000),
						unselectedContentColor = Color(color = 0xFFA2A2A2),
						selected = currentDestination?.hierarchy?.any { it.route == item.screenRoute } == true,
						alwaysShowLabel = false,
						onClick = {
							navigator.navigate(item.screenRoute) {
								// Avoid multiple copies of the same destination when
								// reselecting the same item
								launchSingleTop = true

								// Restore state when reselecting a previously selected item
								restoreState = true
							}
						}
					)
				}
			}
		}
	) { innerPadding ->
		LyfeNavHost(
			modifier = Modifier.padding(innerPadding),
			navHostController = navController,
			navigator = navigator
		)
	}
}

sealed class BottomNavItem(
	@StringRes val title: Int,
	@DrawableRes val icon: Int,
	val description: String,
	val screenRoute: String
) {
	object Home : BottomNavItem(R.string.btm_nav_home, R.drawable.ic_home, "홈 아이콘", LyfeScreens.Home.name)
	object Post : BottomNavItem(R.string.btm_nav_post, R.drawable.ic_post_add, "게시 아이콘", LyfeScreens.Post.name)
	object Alarm : BottomNavItem(R.string.btm_nav_alarm, R.drawable.ic_alarm, "알림 아이콘", LyfeScreens.Alarm.name)
	object Profile : BottomNavItem(R.string.btm_nav_profile, R.drawable.ic_profile, "프로필 아이콘", LyfeScreens.Profile.name)
}