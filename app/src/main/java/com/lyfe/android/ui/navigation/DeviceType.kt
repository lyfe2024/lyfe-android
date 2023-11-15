package com.lyfe.android.ui.navigation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class DeviceType {
	SMALL, LARGE;

	companion object {
		fun getDeviceType(width: Dp) = when {
			width < 300.dp -> SMALL
			else -> LARGE
		}
	}
}