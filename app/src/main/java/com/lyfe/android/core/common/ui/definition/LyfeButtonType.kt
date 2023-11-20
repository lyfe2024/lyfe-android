package com.lyfe.android.core.common.ui.definition

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.ui.theme.Grey200
import com.lyfe.android.ui.theme.Grey50
import com.lyfe.android.ui.theme.Grey500
import com.lyfe.android.ui.theme.Main500

/**
 * TC: TextColor, BC: BgColor, SC: strokeColor
 */
enum class LyfeButtonType(
	val textColor: Color,
	val bgColor: Color,
	val strokeColor: Color,
	val borderWidth: Dp,
	@DrawableRes val closeIcon: Int
) {
	TC_GREY200_BG_TRANSPARENT_SC_GREY200(
		textColor = Grey200,
		bgColor = Color.Transparent,
		strokeColor = Grey200,
		borderWidth = 1.dp,
		closeIcon = R.drawable.ic_circle_close_grey_200
	),
	TC_GREY200_BG_GREY50_SC_GREY200(
		textColor = Grey200,
		bgColor = Grey50,
		strokeColor = Grey200,
		borderWidth = 1.dp,
		closeIcon = R.drawable.ic_circle_close_grey_200
	),
	TC_GREY500_BG_GREY50_SC_TRANSPARENT(
		textColor = Grey500,
		bgColor = Grey50,
		strokeColor = Color.Transparent,
		borderWidth = 0.dp,
		closeIcon = R.drawable.ic_circle_close_grey_500
	),
	TC_WHITE_BG_MAIN500_SC_TRANSPARENT(
		textColor = Color.White,
		bgColor = Main500,
		strokeColor = Color.Transparent,
		borderWidth = 0.dp,
		closeIcon = R.drawable.ic_circle_close_white
	),
}