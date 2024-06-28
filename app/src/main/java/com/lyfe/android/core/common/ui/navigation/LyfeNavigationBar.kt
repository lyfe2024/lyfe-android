package com.lyfe.android.core.common.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.lyfe.android.feature.BottomNavItem

@Composable
fun LyfeNavigationBar(
	bottomNavItems: List<BottomNavItem>,
	selectedItemIndex: Int = 0,
	isNavigationBarHide: Boolean = false,
	createPostButtonsShow: Boolean = false,
	createPostButtonsDismiss: () -> Unit = {},
	onClickPhotoBox: () -> Unit = {},
	onClickTextBox: () -> Unit = {},
	bottomItemClick: (index: Int) -> Unit = {}
) {
	val density = LocalDensity.current
	var navigationBarOffset by remember { mutableStateOf(Offset.Zero) }
	var createPostAreaSize by remember { mutableStateOf(IntSize.Zero) }

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		InteractiveNavigationTab(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.onGloballyPositioned {
					navigationBarOffset = it.boundsInParent().topCenter
				},
			isNavigationBarHide = isNavigationBarHide,
			bottomNavItems = bottomNavItems,
			selectedItemIndex = selectedItemIndex,
			bottomItemClick = bottomItemClick
		)

		if (createPostButtonsShow) {
			CreatePostBoxArea(
				modifier = Modifier
					.onGloballyPositioned {
						createPostAreaSize = it.size
					}
					.offset(
						x = with(density) {
							navigationBarOffset.x.toDp() - createPostAreaSize.width.toDp() / 2
						},
						y = with(density) {
							navigationBarOffset.y.toDp() - createPostAreaSize.height.toDp() - 16.dp
						}
					),
				onDismiss = createPostButtonsDismiss,
				onClickPhotoBox = onClickPhotoBox,
				onClickTextBox = onClickTextBox
			)
		}
	}
}

@Preview
@Composable
fun Preview_LyfeNavigationBar() {
	val bottomNavItems = listOf(
		BottomNavItem.Home,
		BottomNavItem.Feed,
		BottomNavItem.CreatePost,
		BottomNavItem.Alarm,
		BottomNavItem.Profile
	)

	LyfeNavigationBar(
		bottomNavItems = bottomNavItems
	)
}