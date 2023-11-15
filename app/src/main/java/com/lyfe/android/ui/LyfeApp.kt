package com.lyfe.android.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.navigation.LyfeNavHost
import com.lyfe.android.ui.navigation.NavigationTab
import com.lyfe.android.ui.theme.DEFAULT
import com.lyfe.android.ui.theme.Main500

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
		BottomNavItem.ViewAll,
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
		)

		NavigationTab(
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 8.dp, start = 20.dp, end = 20.dp)
				.align(Alignment.BottomCenter),
			items = bottomNavItems,
			selectedItemIndex = selected,
			onClick = {
				selected = it
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

	object ViewAll : BottomNavItem(
		title = R.string.btm_nav_view_all,
		icon = R.drawable.ic_view_all_white,
		description = "홈 아이콘",
		screenRoute = LyfeScreens.Home.name
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