package com.lyfe.android.core.common.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.ScrimColor

@Composable
fun LyfeDialog(
	modifier: Modifier = Modifier,
	isShow: Boolean = true,
	onDismissRequest: () -> Unit = {},
	barWidth: Dp = 40.dp,
	barHeight: Dp = 6.dp,
	barCornerRadius: Dp = 3.dp,
	contentCornerRadius: Dp = 16.dp,
	contentBgColor: Color = Color.White,
	content: @Composable () -> Unit
) {
	Box(
		modifier = modifier.fillMaxSize()
	) {
		LyfeScrim(
			color = ScrimColor,
			onDismissRequest = onDismissRequest,
			visible = isShow
		)

		AnimatedVisibility(
			modifier = modifier.align(Alignment.BottomCenter),
			visible = isShow,
			enter = slideInVertically(
				initialOffsetY = { fullHeight -> fullHeight },
				animationSpec = tween(
					durationMillis = 300,
					delayMillis = 50
				)
			),
			exit = slideOutVertically(
				targetOffsetY = { fullHeight -> fullHeight },
				animationSpec = tween(
					durationMillis = 300,
					delayMillis = 50
				)
			)
		) {
			Column(
				modifier = Modifier.fillMaxWidth()
					.background(
						color = contentBgColor,
						shape = RoundedCornerShape(
							topStart = contentCornerRadius,
							topEnd = contentCornerRadius
						)
					)
			) {
				Box(
					modifier = Modifier.fillMaxWidth()
				) {
					Canvas(
						modifier = Modifier
							.align(Alignment.Center)
							.background(Color.Red)
							.padding(vertical = 8.dp)
					) {
						drawRoundRect(
							color = Grey100,
							topLeft = Offset(x = -(barWidth.toPx()/2), y = 0.dp.toPx()),
							size = Size(barWidth.toPx(), barHeight.toPx()),
							cornerRadius = CornerRadius(barCornerRadius.toPx(), barCornerRadius.toPx())
						)
					}
				}

				content()
			}
		}
	}
}