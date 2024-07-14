package com.lyfe.android.core.common.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lyfe.android.feature.BottomNavItem

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InteractiveNavigationTab(
	modifier: Modifier = Modifier,
	isNavigationBarHide: Boolean,
	bottomNavItems: List<BottomNavItem>,
	selectedItemIndex: Int,
	bottomItemClick: (index: Int) -> Unit
) {
	val density = LocalDensity.current

	AnimatedVisibility(
		modifier = modifier,
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
			selectedItemIndex = selectedItemIndex,
			isNeedIndicatorAnimation = !isNavigationBarHide && !this.transition.isRunning,
			onClick = bottomItemClick
		)
	}
}