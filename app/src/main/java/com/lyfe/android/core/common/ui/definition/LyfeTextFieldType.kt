package com.lyfe.android.core.common.ui.definition

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.Error
import com.lyfe.android.core.common.ui.theme.Grey200

/**
 * TC: TextColor, BC: BgColor, SC: strokeColor
 */
enum class LyfeTextFieldType(
	val textColor: Color,
	val bgColor: Color,
	val strokeColor: Color,
	@DrawableRes val closeIcon: Int? = null
) {
	TC_GREY200_BG_TRANSPARENT_SC_GREY200(Grey200, Color.Transparent, Grey200, null),
	TC_DEFAULT_BG_TRANSPARENT_SC_DEFAULT(DEFAULT, Color.Transparent, Color.Black, R.drawable.ic_circle_close_grey_200),
	TC_ERROR_BG_TRANSPARENT_SC_ERROR(Error, Color.Transparent, Error),
	TC_GREY200_BG_WHITE_SC_TRANSPARENT(Grey200, Color.White, Color.Transparent, R.drawable.ic_circle_close_grey_200),
	TC_DEFAULT_BG_WHITE_SC_TRANSPARENT(DEFAULT, Color.White, Color.Transparent, R.drawable.ic_circle_close_grey_200),
	TC_DEFAULT_BG_WHITE_SC_DEFAULT(DEFAULT, Color.White, DEFAULT, R.drawable.ic_circle_close_grey_200)
}