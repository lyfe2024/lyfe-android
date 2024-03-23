package com.lyfe.android.core.common.ui.component

import androidx.annotation.DrawableRes
import com.lyfe.android.R

enum class LyfeSnackBarIconType(
	@DrawableRes val icon: Int?
) {
	NONE(icon = null),
	ERROR(icon = R.drawable.ic_error_rounded),
	SUCCESS(icon = R.drawable.ic_check_filled)
}