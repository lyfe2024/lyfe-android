package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun DefaultButton(
	modifier: Modifier = Modifier,
	verticalPadding: Dp = 8.dp,
	horizontalPadding: Dp = 24.dp,
	cornerSize: Dp = 6.dp,
	isClearIconShow: Boolean = true,
	buttonType: LyfeButtonType = LyfeButtonType.DISABLED,
	text: String,
	fontSize: Int = 16,
	lineHeight: Int = 24,
	onClose: () -> Unit = {},
	onClick: () -> Unit
) {
	Row(
		modifier = modifier
			.background(
				shape = RoundedCornerShape(size = cornerSize),
				color = buttonType.bgColor
			).clickableSingle {
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
				painter = painterResource(id = buttonType.icon),
				contentDescription = "circle_close_icon"
			)

			Spacer(modifier = Modifier.width(14.dp))
		}

		Text(
			text = text,
			style = TextStyle(
				fontSize = fontSize.sp,
				lineHeight = lineHeight.sp,
				fontWeight = FontWeight.W700,
				color = buttonType.textColor
			)
		)
	}
}

@Preview
@Composable
fun Preview_LyfeButton() {
	Column {
		DefaultButton(
			modifier = Modifier.fillMaxWidth(),
			text = "버튼",
			buttonType = LyfeButtonType.DISABLED
		) {}

		Spacer(modifier = Modifier.height(20.dp))

		DefaultButton(
			modifier = Modifier.fillMaxWidth(),
			text = "버튼",
			buttonType = LyfeButtonType.MAIN_500
		) {}

		Spacer(modifier = Modifier.height(20.dp))

		DefaultButton(
			modifier = Modifier.fillMaxWidth(),
			text = "버튼",
			buttonType = LyfeButtonType.WHITE
		) {}
	}
}