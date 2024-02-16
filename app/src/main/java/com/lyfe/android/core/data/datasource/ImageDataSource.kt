package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetImageUploadUrlResponse
import com.lyfe.android.core.data.network.model.Result
import okhttp3.MultipartBody

interface ImageDataSource {

	suspend fun getImageUploadUrl(
		format: String,
		path: String
	): Result<GetImageUploadUrlResponse>

	suspend fun uploadImage(
		url: String,
		key: String,
		file: MultipartBody.Part
	): Result<Any>
}