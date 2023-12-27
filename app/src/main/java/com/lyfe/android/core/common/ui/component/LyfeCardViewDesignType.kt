package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.component.RatioConstData.FEED_CARD_HEIGHT
import com.lyfe.android.core.common.ui.component.RatioConstData.FEED_CARD_WIDTH
import com.lyfe.android.core.common.ui.component.RatioConstData.MAIN_CARD_HEIGHT
import com.lyfe.android.core.common.ui.component.RatioConstData.MAIN_CARD_WIDTH
import com.lyfe.android.core.common.ui.component.RatioConstData.PROFILE_CARD_HEIGHT
import com.lyfe.android.core.common.ui.component.RatioConstData.PROFILE_CARD_WIDTH
import com.lyfe.android.core.common.ui.theme.pretenard

private object RatioConstData {
	const val MAIN_CARD_WIDTH = 270f
	const val MAIN_CARD_HEIGHT = 358f
	const val FEED_CARD_WIDTH = 152f
	const val FEED_CARD_HEIGHT = 210f
	const val PROFILE_CARD_WIDTH = 152f
	const val PROFILE_CARD_HEIGHT = 210f
}
enum class LyfeCardViewDesignType(
	val userProfileImgSize: Dp,
	val userNameTextStyle: TextStyle,
	val cardContentTextStyle: TextStyle,
	val contentPadding: PaddingValues,
	val ratio: Float
) {
	HOME_SCREEN_CARD(
		userProfileImgSize = 32.dp,
		userNameTextStyle = TextStyle(
			color = Color.White,
			fontSize = 14.sp,
			lineHeight = 22.sp,
			fontWeight = FontWeight.W700,
			fontFamily = pretenard
		),
		cardContentTextStyle = TextStyle(
			color = Color.White,
			fontSize = 20.sp,
			lineHeight = 32.sp,
			fontWeight = FontWeight.W700,
			fontFamily = pretenard
		),
		contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
		ratio = MAIN_CARD_WIDTH / MAIN_CARD_HEIGHT
	),
	FEED_SCREEN_CARD(
		userProfileImgSize = 24.dp,
		userNameTextStyle = TextStyle(
			color = Color.White,
			fontSize = 12.sp,
			lineHeight = 18.sp,
			fontWeight = FontWeight.W600,
			fontFamily = pretenard
		),
		cardContentTextStyle = TextStyle(
			color = Color.White,
			fontSize = 14.sp,
			lineHeight = 22.sp,
			fontWeight = FontWeight.W700,
			fontFamily = pretenard
		),
		contentPadding = PaddingValues(all = 10.dp),
		ratio = FEED_CARD_WIDTH / FEED_CARD_HEIGHT
	),
	PROFILE_SCREEN_CARD(
		userProfileImgSize = 24.dp,
		userNameTextStyle = TextStyle(
			color = Color.White,
			fontSize = 12.sp,
			lineHeight = 18.sp,
			fontWeight = FontWeight.W600,
			fontFamily = pretenard
		),
		cardContentTextStyle = TextStyle(
			color = Color.White,
			fontSize = 14.sp,
			lineHeight = 22.sp,
			fontWeight = FontWeight.W700,
			fontFamily = pretenard
		),
		contentPadding = PaddingValues(all = 10.dp),
		ratio = PROFILE_CARD_WIDTH / PROFILE_CARD_HEIGHT
	)
}