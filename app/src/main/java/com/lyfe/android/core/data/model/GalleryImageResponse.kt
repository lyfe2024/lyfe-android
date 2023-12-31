package com.lyfe.android.core.data.model

import android.net.Uri

data class GalleryImageResponse(
	val id: Long,
	val filepath: String,
	val uri: Uri,
	val name: String,
	val date: String,
	val size: Int,
	var isSelected: Boolean = false
)