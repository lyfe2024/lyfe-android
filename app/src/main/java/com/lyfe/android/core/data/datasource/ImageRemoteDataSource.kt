package com.lyfe.android.core.data.datasource

import com.lyfe.android.core.data.model.UploadImageUrlResult
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.data.network.service.AWSService
import com.lyfe.android.core.data.network.service.ImageService
import okhttp3.RequestBody
import javax.inject.Inject

class ImageRemoteDataSource @Inject constructor(
	private val imageService: ImageService,
	private val awsService: AWSService
) : ImageDataSource {

	override suspend fun getImageUploadUrl(
		format: String,
		path: String
	): Result<UploadImageUrlResult> {
		return imageService.getUploadUrl(format, path)
	}

	override suspend fun uploadImage(url: String, key: String, file: RequestBody): Result<Unit> {
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