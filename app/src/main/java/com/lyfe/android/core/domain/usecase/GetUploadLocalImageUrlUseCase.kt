package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetUploadLocalImageUrlUseCase @Inject constructor(
	private val albumRepository: AlbumRepository,
	private val getUploadImageUrlUseCase: GetUploadImageUrlUseCase
) {
	suspend operator fun invoke(
		localContentUrl: String
	): Flow<String?> {
		val localImageFile = albumRepository.translateToFile(localContentUrl)

		if (localImageFile != null) {
			return getUploadImageUrlUseCase(file = localImageFile)
		} else {
			return flowOf(null)
		}
	}
}