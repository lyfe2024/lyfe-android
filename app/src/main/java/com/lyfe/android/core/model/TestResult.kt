package com.lyfe.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class TestResult(
	val no: Int,
	val type: Int,
	val mark: String,
	val name: String,
	val description: String,
	val promotionDescription: String,
	val people: Int,
	val imageURL: String,
	val backgroundColor: Int,
	val languageCode: String,
	val regionNo: Int,
	val regionCode: String,
	val regionName: String,
	val regionMark: String,
	val isRecommend: Boolean,
	val foundedAt: String?
)