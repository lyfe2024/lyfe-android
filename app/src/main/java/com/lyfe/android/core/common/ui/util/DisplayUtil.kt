package com.lyfe.android.core.common.ui.util

import android.content.Context
import android.util.DisplayMetrics

fun Float.dpToPx(context: Context): Float {
	val displayMetrics = context.resources.displayMetrics
	return this * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.pxToDp(context: Context): Float {
	val displayMetrics = context.resources.displayMetrics
	return this / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}