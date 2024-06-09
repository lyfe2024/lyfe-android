package com.lyfe.android.core.domain.util

import com.lyfe.android.BuildConfig

fun getImageServerUrl(imageKey: String) = "${BuildConfig.AWS_BASE_URL}$imageKey"