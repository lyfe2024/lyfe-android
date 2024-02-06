package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PageInfo(
	val size: Int,
	val number: Int,
	val totalElements: Int,
	val totalPages: Int
)
