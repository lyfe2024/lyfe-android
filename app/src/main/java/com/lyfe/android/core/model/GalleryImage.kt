package com.lyfe.android.core.model

data class GalleryImage(
	val id: Long,
	val filepath: String,
	val imageUri: String,
	val name: String,
	val date: String,
	val size: Int,
	var isSelected: Boolean = false
)