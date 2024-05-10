package com.lyfe.android.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
	val id: Int,
	val content: String,
	val date: String
)