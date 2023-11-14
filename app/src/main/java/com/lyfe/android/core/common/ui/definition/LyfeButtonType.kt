package com.lyfe.android.core.common.ui.definition

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.lyfe.android.R
import com.lyfe.android.ui.theme.Grey200
import com.lyfe.android.ui.theme.Grey500
import com.lyfe.android.ui.theme.Main500

enum class LyfeButtonType(@DrawableRes val icon: Int, val bgColor: Color, val textColor: Color) {
	WHITE(R.drawable.ic_circle_close_grey_500, Color.White, Grey500),
	MAIN_500(R.drawable.ic_circle_close_white, Main500, Color.White),
	DISABLED(R.drawable.ic_circle_close_grey_200, Color.White, Grey200)
}