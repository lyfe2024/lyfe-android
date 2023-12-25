package com.lyfe.android.core.common.ui.navigation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class NavigationShowType {
	ICON, FULL;

	companion object {
		fun getNavigationShowType(width: Dp) = when {
			width < 280.dp -> ICON
			else -> FULL
		}
	}
}