package com.lyfe.android.core.data.mapper

import com.lyfe.android.core.data.model.UploadImageUrlResult
import com.lyfe.android.core.model.ImageKey
import com.lyfe.android.core.model.UploadImageUrl

internal fun UploadImageUrlResult.toDomain() =
	UploadImageUrl(
		url = this.url,
		imageKey = ImageKey(this.key),
		expiresAt = this.expiresAt
	)