package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.util.clickableSingle

@Composable
fun LyfeButton(
	modifier: Modifier = Modifier,
	verticalPadding: Dp = 8.dp,
	horizontalPadding: Dp = 24.dp,
	cornerSize: Dp = 6.dp,
	isClearIconShow: Boolean = true,
	buttonType: LyfeButtonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
	text: String,
	fontSize: Int = 16,
	lineHeight: Int = 24,
	fontWeight: FontWeight = FontWeight.W700,
	onClose: () -> Unit = {},
	onClick: () -> Unit
) {
	Row(
		modifier = modifier
			.background(
				shape = RoundedCornerShape(size = cornerSize),
				color = buttonType.bgColor
			)
			.border(
				width = buttonType.borderWidth,
				color = buttonType.strokeColor,
				shape = RoundedCornerShape(size = cornerSize)
			)
			.clickableSingle {
				onClick()
			}
			.padding(vertical = verticalPadding, horizontal = horizontalPadding),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		if (isClearIconShow) {
			Image(
				modifier = Modifier
					.size(20.dp)
					.clickableSingle { onClose() },
				painter = painterResource(id = buttonType.closeIcon),
				contentDescription = "circle_close_icon"
			)

			Spacer(modifier = Modifier.width(14.dp))
		}

		Text(
			text = text,
			style = TextStyle(
				fontSize = fontSize.sp,
				lineHeight = lineHeight.sp,
				fontWeight = fontWeight,
				color = buttonType.textColor
			)
		)
	}
}

@Preview
@Composable
private fun Preview_LyfeButton() {
	Column {
		LyfeButton(
			modifier = Modifier.fillMaxWidth(),
			text = "버튼",
			buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
		) {}

		Spacer(modifier = Modifier.height(20.dp))

		LyfeButton(
			modifier = Modifier.fillMaxWidth(),
			text = "버튼",
			buttonType = LyfeButtonType.TC_GREY200_BG_GREY50_SC_GREY200
		) {}

		Spacer(modifier = Modifier.height(20.dp))

		LyfeButton(
			modifier = Modifier.fillMaxWidth(),
			text = "버튼",
			buttonType = LyfeButtonType.TC_GREY200_BG_TRANSPARENT_SC_GREY200
		) {}
	}
}