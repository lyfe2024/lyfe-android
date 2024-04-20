package com.lyfe.android.core.common.ui.model

import androidx.annotation.StringRes
import com.lyfe.android.R

enum class NotificationType(val value: String, @StringRes val stringRes: Int) {
	BOARD_CONTENT("BOARD_CONTENT", R.string.noti_list_picture_type_text),
	COMMENT("COMMENT", R.string.noti_list_comments_type_text),
	WHISKY("WHISKY", R.string.noti_list_whisky_type_text),
	BOARD_PICTURE("BOARD_PICTURE", R.string.noti_list_board_type_text);

	companion object {
		fun findByValue(value: String) = values().firstOrNull { it.value == value } ?: BOARD_CONTENT
	}
}