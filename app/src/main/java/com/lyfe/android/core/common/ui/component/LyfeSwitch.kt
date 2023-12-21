package com.lyfe.android.core.common.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyfe.android.ui.theme.Grey100

@Composable
fun LyfeSwitch(
	checked: Boolean = false,
	width: Dp = 46.dp,
	height: Dp = 24.dp,
	checkedThumbColor: Color = Color.White,
	uncheckedThumbColor: Color = Color.White,
	checkedTrackColor: Color = Grey100,
	uncheckedTrackColor: Color = Grey100,
	trackPadding: Dp = 2.dp,
	onCheckedChange: (Boolean) -> Unit
) {

	var switchON by remember { mutableStateOf(checked) }

	val thumbRadius = (height / 2) - trackPadding

	val animatePosition = animateFloatAsState(
		targetValue = if (switchON) {
			with(LocalDensity.current) { (width - thumbRadius - trackPadding).toPx() }
		} else {
			with(LocalDensity.current) { (thumbRadius + trackPadding).toPx() }
		},
		label = "Thumb 애니매이션 포지션 값"
	)

	Canvas(
		modifier = Modifier
			.size(width = width, height = height)
			.pointerInput(Unit) {
				detectTapGestures(
					onTap = {
						switchON = !switchON
						onCheckedChange(switchON)
					}
				)
			}
	) {
		// Track
		drawRoundRect(
			color = if (switchON) checkedTrackColor else uncheckedTrackColor,
			cornerRadius = CornerRadius(x = thumbRadius.toPx(), y = thumbRadius.toPx())
		)

		// Thumb
		drawCircle(
			color = if (switchON) checkedThumbColor else uncheckedThumbColor,
			radius = thumbRadius.toPx(),
			center = Offset(
				x = animatePosition.value,
				y = size.height / 2
			)
		)
	}
}

@Preview
@Composable
private fun Preview_LyfeSwitch() {
	LyfeSwitch {

	}
}