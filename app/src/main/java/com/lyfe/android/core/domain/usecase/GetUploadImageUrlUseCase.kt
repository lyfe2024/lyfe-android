package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.data.network.model.Result
import com.lyfe.android.core.domain.repository.ImageRepository
import com.lyfe.android.core.domain.util.getImageServerUrl
import com.lyfe.android.feature.profileedit.getImageFormat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.io.File
import javax.inject.Inject

/**
 * 해당 UseCase는 순수히 ImageFile을 S3 Bucket에 저장하고 그 url을 반환하는 Usecase입니다.
 * 내부 저장소에 있는 이미지를 보내기위해선 [GetUploadLocalImageUrlUseCase]를 사용해주세요
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GetUploadImageUrlUseCase @Inject constructor(
	private val imageRepository: ImageRepository
) {
	operator fun invoke(
		file: File,
		path: String = "topic_picture"
	) = imageRepository.getImageUploadUrl(
		format = file.getImageFormat(),
		path = path
	).flatMapLatest { uploadImageUrl ->
		// 할당받은 imageUrl 데이터를 가지고 File Upload 진행
		val uploadResult = imageRepository.uploadImage(
			url = uploadImageUrl.url,
			key = uploadImageUrl.imageKey.data,
			file = file
		)

		if (uploadResult is Result.Success) {
			flowOf(getImageServerUrl(uploadImageUrl.imageKey))
		} else {
			flowOf(null)
		}
	}
}