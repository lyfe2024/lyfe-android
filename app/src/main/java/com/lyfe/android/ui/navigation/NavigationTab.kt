package com.lyfe.android.ui.navigation

import android.util.Log
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.lyfe.android.ui.BottomNavItem
import com.lyfe.android.ui.theme.DEFAULT
import com.lyfe.android.ui.theme.Main500

@Composable
fun NavigationTab(
	modifier: Modifier = Modifier,
	selectedItemIndex: Int,
	items: List<BottomNavItem>,
	onClick: (index: Int) -> Unit,
) {
	val density = LocalDensity.current
	var width by remember { mutableStateOf(0.dp) } // 너비 정보를 저장할 변수
	val deviceType = DeviceType.getDeviceType(width)
	val tabWidth = if(deviceType == DeviceType.LARGE) 66.dp else 30.dp
	val itemSpacing = (width - tabWidth * items.size) / (items.size - 1)

	val indicatorOffset: Dp by animateDpAsState(
		targetValue = (tabWidth+itemSpacing) * selectedItemIndex,
		animationSpec = tween(easing = LinearEasing, durationMillis = 200), label = "",
	)

	Box(
		modifier = modifier
			.background(color = DEFAULT, shape = RoundedCornerShape(20.dp))
			.height(56.dp)
			.padding(vertical = 8.dp, horizontal = 20.dp)
			.onGloballyPositioned {
				width = with(density) {
					it.size.width.toDp()
				}
			},
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
			verticalAlignment = Alignment.CenterVertically,
		) {
			List(items.size) { index ->
				val isSelected = index == selectedItemIndex

				MyTabItem(
					modifier = Modifier.onGloballyPositioned {
						if (index == selectedItemIndex) {
							val itemWidth = with(density) {
								it.size.width.toDp()
							}
						}
					},
					isSelected = isSelected,
					deviceType = deviceType,
					onClick = {
						onClick(index)
					},
					tabWidth = tabWidth,
					item = items[index],
				)
			}
		}
	}
}

@Composable
private fun MyTabIndicator(
	indicatorWidth: Dp,
	indicatorOffset: Dp,
	indicatorColor: Color,
) {
	Box(
		modifier = Modifier
			.fillMaxHeight()
			.width(
				width = indicatorWidth,
			)
			.offset(
				x = indicatorOffset,
			)
			.clip(
				shape = CircleShape,
			)
			.background(
				color = indicatorColor,
			),
	)
}

@Composable
private fun MyTabItem(
	modifier: Modifier = Modifier,
	isSelected: Boolean,
	deviceType: DeviceType = DeviceType.LARGE,
	onClick: () -> Unit,
	tabWidth: Dp,
	item: BottomNavItem,
) {
	val iconSize = if (deviceType == DeviceType.SMALL) {
		12.dp
	} else {
		24.dp
	}
	val tabColor: Color by animateColorAsState(
		targetValue = if (isSelected) {
			Main500
		} else {
			Color.Transparent
		},
		animationSpec = tween(easing = LinearEasing), label = "",
	)

	Row(
		modifier = Modifier
			.width(tabWidth)
			.clickableSingle {
				onClick()
			},
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {

		Image(
//			modifier = Modifier.size(iconSize),
			painter = painterResource(id = item.icon),
			contentDescription = ""
		)

		if (isSelected && deviceType == DeviceType.LARGE) {
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
					textAlign = TextAlign.Center,
				)
			)
		}
	}
}