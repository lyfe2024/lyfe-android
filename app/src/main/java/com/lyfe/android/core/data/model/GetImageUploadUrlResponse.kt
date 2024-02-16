package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetImageUploadUrlResponse(
	val result: UploadImageUrlResult
)

@Serializable
data class UploadImageUrlResult(
	val url: String,
	val key: String,
	val expiresAt: String
)