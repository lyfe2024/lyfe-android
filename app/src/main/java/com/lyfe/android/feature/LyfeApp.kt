package com.lyfe.android.feature

import android.util.Log
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeSnackBar
import com.lyfe.android.core.common.ui.component.LyfeSnackBarVisuals
import com.lyfe.android.core.common.ui.navigation.NavigationTab
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.ScrimColor
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeNavHost
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LyfeApp(
	navigator: LyfeNavigator
) {
	val density = LocalDensity.current
	val navController = rememberNavController()
	val scrollState = rememberScrollState()
	var snackBarVisuals by remember { mutableStateOf(LyfeSnackBarVisuals()) }
	val snackBarState = remember { SnackbarHostState() }

	var isNavigationBarHide by remember { mutableStateOf(false) }
	var isNeedNavigationTabIndicatorAnimation by remember { mutableStateOf(false) }

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
	var bottomNaviTopOffsetY by remember { mutableStateOf(0.dp) }
	var createPostButtonsShow by remember { mutableStateOf(false) }

	Box(
		modifier = Modifier.fillMaxSize()
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
			Log.e("Test@@@", "route: $route")
			showBtmNavi = bottomNavItems.any { item -> item.screenRoute == route }
		}

		if (createPostButtonsShow) {
			Spacer(
				modifier = Modifier
					.fillMaxSize()
					.background(ScrimColor)
					.clickableSingle {
						createPostButtonsShow = false
						btmSelectedIdx = prevbtmSelectedIdx
					}
			)

			Column(
				modifier = Modifier.offset(y = bottomNaviTopOffsetY - 16.dp - 8.dp - 64.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				CreatePostBox(
					modifier = Modifier.width(144.dp),
					textRes = R.string.create_board_picture,
					iconRes = R.drawable.ic_pic_fill,
					click = {
						createPostButtonsShow = false
						navigator.navigate(LyfeScreens.CreatePhotoPost.name)
					}
				)

				Spacer(modifier = Modifier
					.fillMaxWidth()
					.height(8.dp))

				CreatePostBox(
					modifier = Modifier.width(144.dp),
					textRes = R.string.create_board,
					iconRes = R.drawable.ic_text,
					click = {
						createPostButtonsShow = false
						navigator.navigate(LyfeScreens.CreatePhotoPost.name)
					}
				)

				Spacer(modifier = Modifier
					.fillMaxWidth()
					.height(16.dp))

			}
		}

		if (showBtmNavi) {
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
						.padding(bottom = 8.dp, start = 20.dp, end = 20.dp)
						.onGloballyPositioned {
							with(density) {
								bottomNaviTopOffsetY = it.boundsInRoot().top.toDp()
							}
						},
					items = bottomNavItems,
					selectedItemIndex = btmSelectedIdx,
					isNeedIndicatorAnimation = !isNavigationBarHide && !this.transition.isRunning,
					onClick = { index ->
						prevbtmSelectedIdx = btmSelectedIdx
						btmSelectedIdx = index

						createPostButtonsShow = bottomNavItems[index] == BottomNavItem.CreatePost
						if (bottomNavItems[index] != BottomNavItem.CreatePost) {
							navigator.navigate(bottomNavItems[index].screenRoute)
						}
					}
				)
			}
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

@Composable
private fun CreatePostBox(
	modifier: Modifier = Modifier,
	@StringRes textRes: Int,
	@DrawableRes iconRes: Int,
	click: () -> Unit
)  {
	Row(
		modifier
			.background(color = Main500, shape = RoundedCornerShape(12.dp))
			.clickableSingle { click() }
			.padding(horizontal = 16.dp, vertical = 5.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			modifier = Modifier.size(16.dp),
			painter = painterResource(id = iconRes),
			contentDescription = "icon",
			tint = Color.White
		)

		Text(
			text = stringResource(id = textRes),
			style = Title3,
			color = Color.White
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