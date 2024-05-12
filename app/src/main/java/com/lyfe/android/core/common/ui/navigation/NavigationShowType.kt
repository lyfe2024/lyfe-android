package com.lyfe.android.core.common.ui.navigation

enum class NavigationShowType {
	ICON, FULL;

	companion object {
		/*
		 * 텍스트 노출이 필요 시 280.dp 미만일 경우 ICON, else FULL
		 */
		fun getNavigationShowType() = ICON
	}
}