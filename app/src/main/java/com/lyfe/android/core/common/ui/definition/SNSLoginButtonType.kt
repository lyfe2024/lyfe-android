package com.lyfe.android.core.common.ui.definition

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.lyfe.android.R
import com.lyfe.android.ui.theme.DEFAULT
import com.lyfe.android.ui.theme.KakaoYelowColor

/**
 * TC: TextColor, BC: BgColor
 */
enum class SNSLoginButtonType(
	val textColor: Color,
	val bgColor: Color,
	@StringRes val textRes: Int,
	@DrawableRes val iconRes: Int
) {
	Kakao(
		textColor = DEFAULT,
		bgColor = KakaoYelowColor,
		textRes = R.string.kakao_button_text,
		iconRes = R.drawable.ic_kakao_talk
	),
	Apple(
		textColor = Color.White,
		bgColor = Color.Black,
		textRes = R.string.apple_button_text,
		iconRes = R.drawable.ic_apple
	),
	Google(
		textColor = DEFAULT,
		bgColor = Color.White,
		textRes = R.string.google_button_text,
		iconRes = R.drawable.ic_google
	)
}