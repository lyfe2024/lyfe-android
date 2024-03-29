package com.lyfe.android.feature.profileedit

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Green50
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Red50

enum class NicknameInvalidState(
	val color: Color,
	@DrawableRes val icon: Int
) {
	EMPTY(
		color = Grey200,
		icon = R.drawable.ic_check_gray
	),
	CORRECT(
		color = Green50,
		icon = R.drawable.ic_check_blue
	),
	INCORRECT(
		color = Red50,
		icon = R.drawable.ic_error
	)
}