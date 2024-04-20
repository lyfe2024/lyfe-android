package com.lyfe.android.core.common.ui.model

import androidx.annotation.StringRes
import com.lyfe.android.R

enum class NotificationType(val value: String, @StringRes val stringRes: Int) {
	BOARD_COMMENT("BOARD_COMMENT", R.string.noti_list_board_comment_text),
	BOARD_WHISKY("BOARD_WHISKY", R.string.noti_list_board_whisky_text),
	PICTURE_COMMENT("BOARD_PICTURE_COMMENT", R.string.noti_list_picture_comment_type_text),
	PICTURE_WHISKY("BOARD_PICTURE_WHISKY", R.string.noti_list_picture_whisky_type_text);

	companion object {
		fun findByValue(value: String) = values().firstOrNull { it.value == value } ?: BOARD_COMMENT
	}
}