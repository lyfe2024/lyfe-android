package com.lyfe.android.feature.setting

import androidx.annotation.StringRes
import com.lyfe.android.R

enum class Setting(
	@StringRes val content: Int
) {
	NOTIFICATION(R.string.setting_screen_notification),
	USER_EXPERIENCE(R.string.feedback_title),
	TERMS(R.string.setting_screen_terms),
	PRIVACY_POLICY(R.string.setting_screen_privacy_policy),
	DELETE_ACCOUNT(R.string.setting_screen_delete_account)
}