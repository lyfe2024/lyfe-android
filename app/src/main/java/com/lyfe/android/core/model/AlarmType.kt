package com.lyfe.android.core.model

import androidx.annotation.StringRes
import com.lyfe.android.R

enum class AlarmType(@StringRes val stringRes: Int) {
	REQUEST_PHOTO_REACTION(R.string.request_photo_reaction),
	PHOTO_COMMENTS(R.string.photo_comments),
	POST_REACTION(R.string.post_reaction),
	POST_COMMENTS(R.string.post_comments)
}