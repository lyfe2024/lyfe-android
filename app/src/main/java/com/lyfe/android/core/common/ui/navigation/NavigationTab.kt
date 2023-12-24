package com.lyfe.android.core.common.ui.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.feature.BottomNavItem
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.Main500

@Composable
fun NavigationTab(
	modifier: Modifier = Modifier,
	selectedItemIndex: Int,
	items: List<BottomNavItem>,
	isNeedIndicatorAnimation: Boolean = true,
	onClick: (index: Int) -> Unit
) {
	val density = LocalDensity.current
	var width by remember { mutableStateOf(0.dp) } // 너비 정보를 저장할 변수
	val navigationShowType = NavigationShowType.getNavigationShowType(width)
	var tabWidth by remember { mutableStateOf(0.dp) }
	var indicatorPosition by remember { mutableStateOf(0.dp) }

	val indicatorOffset: Dp by animateDpAsState(
		targetValue = indicatorPosition,
		animationSpec = tween(
			easing = LinearEasing,
			durationMillis = if (isNeedIndicatorAnimation) 200 else 0
		),
		label = ""
	)

	Box(
		modifier = modifier
			.background(color = DEFAULT, shape = RoundedCornerShape(20.dp))
			.height(56.dp)
			.padding(vertical = 8.dp, horizontal = 20.dp)
			.onGloballyPositioned {
				width = with(density) { it.size.width.toDp() }
			}
	) {
		MyTabIndicator(
			indicatorWidth = tabWidth,
			indicatorOffset = indicatorOffset,
			indicatorColor = Main500
		)

		Row(
			modifier = Modifier
				.fillMaxSize(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			List(items.size) { index ->
				val isSelected = index == selectedItemIndex

				MyTabItem(
					modifier = Modifier.onGloballyPositioned {
						if (isSelected) {
							indicatorPosition = with(density) {
								it.positionInWindow().x.toDp() - 40.dp
							}
							tabWidth = with(density) {
								it.size.width.toDp()
							}
						}
					},
					isSelected = isSelected,
					navigationShowType = navigationShowType,
					onClick = {
						onClick(index)
					},
					item = items[index]
				)
			}
		}
	}
}

@Composable
private fun MyTabIndicator(
	indicatorWidth: Dp,
	indicatorOffset: Dp,
	indicatorColor: Color
) {
	Box(
		modifier = Modifier
			.fillMaxHeight()
			.width(width = indicatorWidth)
			.offset(x = indicatorOffset)
			.clip(shape = RoundedCornerShape(12.dp))
			.background(color = indicatorColor)
	)
}

@Composable
private fun MyTabItem(
	modifier: Modifier = Modifier,
	isSelected: Boolean,
	navigationShowType: NavigationShowType = NavigationShowType.FULL,
	onClick: () -> Unit,
	item: BottomNavItem
) {
	Row(
		modifier = modifier
			.padding(vertical = 6.dp, horizontal = 12.dp)
			.clickableSingle {
				onClick()
			},
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		Image(
			painter = painterResource(id = item.getIcon(isSelected)),
			contentDescription = item.description
		)

		if (isSelected && navigationShowType == NavigationShowType.FULL) {
			Spacer(modifier = Modifier.width(4.dp))

			Text(
				text = stringResource(id = item.title),
				color = Color.White,
				textAlign = TextAlign.Center,
				style = TextStyle(
					fontSize = 12.sp,
					lineHeight = 16.sp,
					fontWeight = FontWeight.W700,
					color = Color.White,
					textAlign = TextAlign.Center
				)
			)
		}
	}
}