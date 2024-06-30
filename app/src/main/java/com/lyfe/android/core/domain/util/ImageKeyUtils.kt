package com.lyfe.android.core.domain.util

import com.lyfe.android.BuildConfig
import com.lyfe.android.core.model.ImageKey

fun getImageServerUrl(imageKey: ImageKey)
	= "${BuildConfig.AWS_BASE_URL}/${imageKey.removeLeadingSlash()}"