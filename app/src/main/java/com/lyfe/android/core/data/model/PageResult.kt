package com.lyfe.android.core.data.model

import com.lyfe.android.core.model.Page
import kotlinx.serialization.Serializable

@Serializable
data class PageResult(
	val size: Int,
	val number: Int,
	val totalElements: Int,
	val totalPages: Int
) {
	fun toDomain() = Page(
		size = size,
		number = number,
		totalElements = totalElements,
		totalPages = totalPages
	)
}
