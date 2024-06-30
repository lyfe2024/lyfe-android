package com.lyfe.android.core.model

@JvmInline
value class ImageKey(
	val data: String
) {
	fun removeLeadingSlash(): String {
		return if (data.startsWith("/")) data.substring(1) else data
	}
}