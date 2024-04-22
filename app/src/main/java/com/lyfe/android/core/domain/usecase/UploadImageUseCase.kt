package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.ImageRepository
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
	private val imageRepository: ImageRepository
) {

	suspend operator fun invoke(
		url: String,
		key: String,
		file: File
	) = imageRepository.uploadImage(url, key, file)
}