package com.lyfe.android.core.data.repository

import android.util.Log
import com.lyfe.android.core.data.datasource.ImageDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.data.network.model.ApiResultException
import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
	private val imageDataSource: ImageDataSource
) : ImageRepository {

	override fun getImageUploadUrl(format: String, path: String) = flow {
		Log.w("Test@@@", "repository getImageUploadUrl: ${imageDataSource.getImageUploadUrl(
			format = format,
			path = path
		)}")
		when (
			val response = imageDataSource.getImageUploadUrl(
				format = format,
				path = path
			)
		) {
			is Result.Success -> {
				val result = response.body
				emit(result.toDomain())
			}
			is Result.Failure -> {
				throw ApiResultException(response.error)
			}
			is Result.NetworkError -> {
				throw response.exception
			}
			is Result.Unexpected -> {
				throw response.t
			}
		}
	}.flowOn(ioDispatcher)

	override suspend fun uploadImage(url: String, key: String, file: File): Result<Unit> {
		val requestBody = file.asRequestBody()
		Log.w("Test@@@", "repository uploadImage ${requestBody.contentType()}")

		return imageDataSource.uploadImage(
			url = url,
			key = key,
			file = requestBody
		)
	}
}