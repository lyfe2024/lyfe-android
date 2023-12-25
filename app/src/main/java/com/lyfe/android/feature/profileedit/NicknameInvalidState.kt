package com.lyfe.android.feature.profileedit

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Error
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Success

enum class NicknameInvalidState(
	val color: Color,
	@DrawableRes val icon: Int
) {
	EMPTY(
		color = Grey200,
		icon = R.drawable.ic_check_gray
	),
	CORRECT(
		color = Success,
		icon = R.drawable.ic_check_blue
	),
	INCORRECT(
		color = Error,
		icon = R.drawable.ic_error
	)
}