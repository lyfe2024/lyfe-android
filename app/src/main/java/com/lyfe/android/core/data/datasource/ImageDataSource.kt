package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.UploadImageUrlResult
import com.lyfe.android.core.data.network.model.Result
import okhttp3.MultipartBody

interface ImageDataSource {

	suspend fun getImageUploadUrl(
		format: String,
		path: String
	): Result<UploadImageUrlResult>

	suspend fun uploadImage(
		url: String,
		key: String,
		file: MultipartBody.Part
	): Result<Any>
}