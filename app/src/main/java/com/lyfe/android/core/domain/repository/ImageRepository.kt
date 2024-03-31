package com.lyfe.android.core.domain.repository

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.model.UploadImageUrl
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ImageRepository {

	fun getImageUploadUrl(
		format: String,
		path: String
	): Flow<UploadImageUrl>

	suspend fun uploadImage(
		url: String,
		key: String,
		file: File
	): Result<Any>
}