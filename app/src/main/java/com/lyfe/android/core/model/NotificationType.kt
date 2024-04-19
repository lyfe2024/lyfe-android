package com.lyfe.android.core.model

enum class NotificationType(val value: String) {
	BOARD_CONTENT("BOARD_CONTENT"),
	COMMENT("COMMENT"),
	WHISKY("WHISKY"),
	BOARD_PICTURE("BOARD_PICTURE");

	companion object {
		fun findByValue(value: String) = values().firstOrNull { it.value == value } ?:BOARD_CONTENT
	}
}