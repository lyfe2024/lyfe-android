package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageUploadUrlUseCase @Inject constructor(
	private val imageRepository: ImageRepository
) {
	// 이미지 업로드용 url 불러오기
	operator fun invoke(format: String, path: String) = imageRepository.getImageUploadUrl(format, path)
}