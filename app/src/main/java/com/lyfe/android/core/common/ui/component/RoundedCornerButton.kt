package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.util.clickableSingle

@Composable
fun RoundedCornerButton(
	modifier: Modifier = Modifier,
	cornerRadius: Dp = 10.dp,
	horizontalPadding: Dp = 0.dp,
	verticalPadding: Dp = 0.dp,
	isClickableColor: Color,
	isNotClickableColor: Color = Color.Unspecified,
	isClickable: Boolean = true,
	onClick: () -> Unit,
	content: @Composable () -> Unit,
) {

	Box(
		modifier = modifier
			.background(
				shape = RoundedCornerShape(cornerRadius),
				color = if (isClickable) {
					isClickableColor
				} else {
					isNotClickableColor
				}
			)
			.padding(vertical = verticalPadding, horizontal = horizontalPadding)
			.clickableSingle (
				enabled = isClickable,
				onClick = onClick
			),
	) {
		Box(modifier = Modifier.align(Alignment.Center)) {
			content()
		}
	}
}