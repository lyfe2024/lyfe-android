package com.lyfe.android.core.common.ui.definition

import androidx.annotation.DrawableRes
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
	val text: String,
	@DrawableRes val icon: Int
) {
	Kakao(
		textColor = DEFAULT,
		bgColor = KakaoYelowColor,
		text =  "카카오로 3초만에 시작하기",
		icon = R.drawable.ic_kakao_talk
	),
	Apple(
		textColor = Color.White,
		bgColor = Color.Black,
		text = "Apple로 계속하기",
		icon = R.drawable.ic_apple
	),
	Google(
		textColor = DEFAULT,
		bgColor = Color.White,
		text = "Google로 계속하기",
		icon = R.drawable.ic_google
	)
}