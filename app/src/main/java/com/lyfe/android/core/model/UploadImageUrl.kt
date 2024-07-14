package com.lyfe.android.core.model

data class UploadImageUrl(
	val url: String,
	val imageKey: ImageKey,
	val expiresAt: String?
)