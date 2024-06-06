package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.UploadImageUrlResult
import com.lyfe.android.core.data.network.model.Result
import okhttp3.RequestBody

interface ImageDataSource {

	suspend fun getImageUploadUrl(
		format: String,
		path: String
	): Result<UploadImageUrlResult>

	suspend fun uploadImage(
		url: String,
		key: String,
		file: RequestBody
	): Result<Unit>
}