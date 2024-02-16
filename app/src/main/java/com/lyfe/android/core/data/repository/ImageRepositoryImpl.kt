package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.ImageDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
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
		when (val response = imageDataSource.getImageUploadUrl(format, path)) {
			is Result.Success -> {
				val result = response.body!!.result
				emit(result.toDomain())
			}
			is Result.Failure -> {
				throw RuntimeException(response.error)
			}
			is Result.NetworkError -> {
				throw RuntimeException(response.exception)
			}
			is Result.Unexpected -> {
				throw RuntimeException(response.t)
			}
		}
	}.flowOn(ioDispatcher)

	override suspend fun uploadImage(url: String, key: String, file: File): Result<Any> {
		val requestBody = file.asRequestBody()
		val part = MultipartBody.Part.createFormData(
			"image",
			file.name,
			requestBody
		)
		return imageDataSource.uploadImage(url, key, part)
	}
}