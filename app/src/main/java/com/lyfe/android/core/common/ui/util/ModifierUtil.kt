package com.lyfe.android.core.common.ui.util

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
	clickable(
		indication = null,
		interactionSource = remember { MutableInteractionSource() }
	) {
		onClick()
	}
}

fun Modifier.clickableSingle(
	enabled: Boolean = true,
	onClickLabel: String? = null,
	role: Role? = null,
	onClick: () -> Unit
) = composed(
	inspectorInfo = debugInspectorInfo {
		name = "clickable"
		properties["enabled"] = enabled
		properties["onClickLabel"] = onClickLabel
		properties["role"] = role
		properties["onClick"] = onClick
	}
) {
	val multipleEventsCutter = remember { MultipleEventsBlockManager.get() }
	Modifier.clickable(
		enabled = enabled,
		onClickLabel = onClickLabel,
		onClick = { multipleEventsCutter.processEvent { onClick() } },
		role = role,
		indication = null,
		interactionSource = remember { MutableInteractionSource() }
	)
}

fun Modifier.shadow(
	color: Color = Color.Black,
	offsetX: Dp = 0.dp,
	offsetY: Dp = 0.dp,
	blurRadius: Dp = 0.dp
) = then(
	drawBehind {
		drawIntoCanvas { canvas ->
			val paint = Paint()
			val frameworkPaint = paint.asFrameworkPaint()
			if (blurRadius != 0.dp) {
				frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
			}
			frameworkPaint.color = color.toArgb()

			val leftPixel = offsetX.toPx()
			val topPixel = offsetY.toPx()
			val rightPixel = size.width + topPixel
			val bottomPixel = size.height + leftPixel

			canvas.drawRect(
				left = leftPixel,
				top = topPixel,
				right = rightPixel,
				bottom = bottomPixel,
				paint = paint
			)
		}
	}
)