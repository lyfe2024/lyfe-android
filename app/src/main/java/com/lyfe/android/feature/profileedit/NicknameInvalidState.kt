package com.lyfe.android.feature.profileedit

import androidx.compose.ui.graphics.Color
import com.lyfe.android.R
import com.lyfe.android.ui.theme.Error
import com.lyfe.android.ui.theme.Grey200
import com.lyfe.android.ui.theme.Success

enum class NicknameInvalidState(
	val color: Color,
	val icon: Int
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