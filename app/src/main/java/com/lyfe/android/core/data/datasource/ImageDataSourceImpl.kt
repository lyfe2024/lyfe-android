package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.GetImageUploadUrlResponse
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AWSService
import com.lyfe.android.core.data.network.service.ImageService
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
	private val imageService: ImageService,
	private val awsService: AWSService
) : ImageDataSource {

	override suspend fun getImageUploadUrl(
		format: String,
		path: String
	): Result<GetImageUploadUrlResponse> {
		return imageService.getUploadUrl(format, path)
	}

	override suspend fun uploadImage(url: String, key: String, file: MultipartBody.Part): Result<Any> {
		val queryMap: HashMap<String, String> = hashMapOf()

		val splitKey = key.split("/")
		val queryUrl = url.substring(url.indexOf('?') + 1)
		queryUrl.split('&').map {
			val split = it.split('=')
			queryMap[split[0]] = split[1]
		}

		return awsService.putImage(
			dev = splitKey[0],
			path = splitKey[1],
			fileName = splitKey[2],
			queryMap = queryMap,
			body = file
		)
	}
}