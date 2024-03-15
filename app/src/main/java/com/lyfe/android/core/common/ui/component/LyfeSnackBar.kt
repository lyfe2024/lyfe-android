package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.pretenard

private const val BACKGROUND_ALPHA = 0.8f

@Composable
fun LyfeSnackBar(
	iconType: LyfeSnackBarIconType,
	message: String
) {
	Row(
		modifier = Modifier
			.background(
				color = DEFAULT.copy(BACKGROUND_ALPHA),
				shape = RoundedCornerShape(10.dp)
			)
			.padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		Image(
			painter = painterResource(id = iconType.icon),
			contentDescription = null
		)

		Text(
			text = message,
			style = TextStyle(
				color = Color.White,
				fontWeight = FontWeight.W500,
				fontSize = 14.sp,
				fontFamily = pretenard
			)
		)
	}
}

@Preview
@Composable
fun LyfeSnackBar_Preview() {
	LyfeSnackBar(
		iconType = LyfeSnackBarIconType.ERROR,
		message = "저장에 실패했어요. 잠시 후 다시 시도해주세요."
	)
}