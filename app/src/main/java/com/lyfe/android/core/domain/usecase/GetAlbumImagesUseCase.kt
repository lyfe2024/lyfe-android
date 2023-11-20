package com.lyfe.android.core.domain.usecase

import com.lyfe.android.core.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumImagesUseCase @Inject constructor(
	private val albumRepository: AlbumRepository
) {

	operator fun invoke() = albumRepository.getAllPhotos()
}